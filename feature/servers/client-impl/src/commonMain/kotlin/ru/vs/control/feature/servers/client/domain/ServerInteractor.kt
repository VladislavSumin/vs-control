package ru.vs.control.feature.servers.client.domain

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.control.feature.servers.client.repository.ServerId
import ru.vs.core.coroutines.share
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.connector.ktorWebsocket.RSubConnectorKtorWebSocket

/**
 * Отвечает за сервер с конкретным [id].
 * Всегда создается ровно один инстанс на каждый id. При изменении другой информации о сервере не пересоздается
 */
internal interface ServerInteractor {
    val id: ServerId
    val server: Flow<Server>
    val connectionStatus: Flow<RSubConnectionStatus>
}

@GenerateFactory
internal class ServerInteractorImpl(
    @ByCreate override val id: ServerId,
    @ByCreate override val server: StateFlow<Server>,
    httpClient: HttpClient,
    scope: CoroutineScope,
) : ServerInteractor {

    val client: SharedFlow<RSubClient> = server
        .map { it.host }
        .distinctUntilChanged()
        .map {
            RSubClient(
                connector = RSubConnectorKtorWebSocket(httpClient, it),
            )
        }
        .share(scope)

    override val connectionStatus: Flow<RSubConnectionStatus> = client.flatMapLatest { it.observeConnectionStatus() }
}
