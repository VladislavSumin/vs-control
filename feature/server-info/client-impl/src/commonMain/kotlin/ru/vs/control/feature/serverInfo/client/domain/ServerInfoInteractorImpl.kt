package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApi
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor.ConnectionStatusWithServerInfo
import ru.vs.control.feature.serverInfo.rSub.ServerInfoRsubRSubImpl
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.ktor.client.mapSuccess
import ru.vs.rsub.RSubConnectionStatus

internal class ServerInfoInteractorImpl(
    private val serverInfoApi: ServerInfoApi,
    private val serversInteractor: ServersInteractor,
) : ServerInfoInteractor {
    override suspend fun getInitialServerInfo(url: Url): SafeResponse<ServerInfo> {
        return serverInfoApi.getServerInfo(url)
            .mapSuccess { ServerInfo(it.name, it.version) }
    }

    override fun observeServerInfo(serverId: ServerId): Flow<ServerInfo> {
        return serversInteractor.observeServerInteractor(serverId)
            .filterNotNull()
            .flatMapLatest { serverInteractor ->
                serverInteractor.rSubInterface(::ServerInfoRsubRSubImpl)
            }
            .flatMapLatest { it.observeServerInfo().map { ServerInfo(it.name, it.version) } }
    }

    override fun observeConnectionStatusWithServerInfo(serverId: ServerId): Flow<ConnectionStatusWithServerInfo> {
        return serversInteractor.observeServerInteractor(serverId)
            .filterNotNull()
            .flatMapLatest { it.connectionStatus }
            .flatMapLatest { connectionStatus ->
                when (connectionStatus) {
                    is RSubConnectionStatus.Connected -> {
                        observeServerInfo(serverId).map {
                            ConnectionStatusWithServerInfo.Connected(it)
                        }
                    }

                    is RSubConnectionStatus.Connecting -> flowOf(ConnectionStatusWithServerInfo.Connecting)

                    is RSubConnectionStatus.Reconnecting -> flowOf(
                        ConnectionStatusWithServerInfo.Reconnecting(
                            connectionStatus.connectionError,
                        ),
                    )
                }
            }
    }
}
