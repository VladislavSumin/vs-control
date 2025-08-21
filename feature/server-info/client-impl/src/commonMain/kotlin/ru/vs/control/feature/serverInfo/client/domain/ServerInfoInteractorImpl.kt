package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApi
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.ktor.client.mapSuccess

internal class ServerInfoInteractorImpl(
    private val serverInfoApi: ServerInfoApi,
    private val serversInteractor: ServersInteractor,
) : ServerInfoInteractor {
    override suspend fun getInitialServerInfo(url: Url): SafeResponse<ServerInfo> {
        return serverInfoApi.getServerInfo(url)
            .mapSuccess { ServerInfo(it.name, it.version) }
    }

    override fun observeServerInfo(serverId: ServerId): Flow<ServerInfo> {
        TODO("Not yet implemented")
    }
}
