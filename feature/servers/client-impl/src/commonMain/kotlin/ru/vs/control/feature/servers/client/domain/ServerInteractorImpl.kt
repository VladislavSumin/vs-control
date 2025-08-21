package ru.vs.control.feature.servers.client.domain

import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.servers.client.domain.ServerInteractor.ConnectionStatus
import ru.vs.core.coroutines.share
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.connector.ktorWebsocket.RSubConnectorKtorWebSocket

@GenerateFactory
internal class ServerInteractorImpl(
    @ByCreate override val id: ServerId,
    @ByCreate override val server: StateFlow<Server>,
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
                        URLProtocol.Companion.WSS
                    } else {
                        URLProtocol.Companion.WS
                    },
                    host = server.host,
                    port = server.port,
                ),
            )
        }
        .share(scope)

    override val connectionStatus: Flow<ConnectionStatus> = client
        .flatMapLatest { it.observeConnectionStatus() }
        .map {
            when (it) {
                RSubConnectionStatus.Connected -> ConnectionStatus.Connected
                RSubConnectionStatus.Connecting -> ConnectionStatus.Connecting
                is RSubConnectionStatus.Reconnecting -> ConnectionStatus.Reconnecting(it.connectionError)
            }
        }
}
