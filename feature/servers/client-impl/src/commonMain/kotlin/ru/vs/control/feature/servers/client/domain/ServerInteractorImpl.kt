package ru.vs.control.feature.servers.client.domain

import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.serialization.protobuf.ProtoBuf
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.core.coroutines.share
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.RSubFlowDisconnectedException
import ru.vs.rsub.connector.ktorWebsocket.RSubConnectorKtorWebSocket

@GenerateFactory
internal class ServerInteractorImpl(
    @ByCreate override val id: ServerId,
    @ByCreate override val server: StateFlow<Server>,
    protoBuf: ProtoBuf,
    httpClient: HttpClient,
    scope: CoroutineScope,
) : ServerInteractor {

    /**
     * rSub клиент для соединения с сервером.
     *
     * Является [kotlinx.coroutines.flow.SharedFlow], так как при редактировании сервера может измениться его url с
     * сохранением текущего id.
     */
    private val client: SharedFlow<RSubClient> = server
        .map { server ->
            RSubClient(
                connector = RSubConnectorKtorWebSocket(
                    client = httpClient,
                    protocol = if (server.isSecure) {
                        URLProtocol.WSS
                    } else {
                        URLProtocol.WS
                    },
                    host = server.host,
                    port = server.port,
                ),
                protobuf = protoBuf,
            )
        }
        .share(scope)

    override val connectionStatus: Flow<RSubConnectionStatus> = client.flatMapLatest { it.observeConnectionStatus() }

    override fun <T, V> withRSub(
        factory: (RSubClient) -> T,
        block: (T) -> Flow<V>,
    ): Flow<V> {
        return client.map(factory).flatMapLatest(block)
    }

    override fun <T, V> withConnectedRSub(
        factory: (RSubClient) -> T,
        onConnectionError: () -> V,
        block: (T) -> Flow<V>,
    ): Flow<V> = channelFlow {
        client.collectLatest { client ->
            client.observeConnectionStatus()
                .map { it is RSubConnectionStatus.Connected }
                .distinctUntilChanged()
                .collect { isConnected ->
                    if (isConnected) {
                        try {
                            block(factory(client)).collect(channel::send)
                        } catch (_: RSubFlowDisconnectedException) {
                            // Нам не нужно ничего делать в этом месте. Данная ошибка всегда означает что клиент
                            // переходит в состояние disconnected, а потому мы по условию выше перейдем в правильное
                            // состояние.
                        }
                    } else {
                        channel.send(onConnectionError())
                    }
                }
        }
    }
}
