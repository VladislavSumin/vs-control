package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApi
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.ktor.client.mapSuccess

internal class ServerInfoInteractorImpl(
    private val serverInfoApi: ServerInfoApi,
) : ServerInfoInteractor {
    override suspend fun getServerInfo(url: Url): SafeResponse<ServerInfo> {
        return serverInfoApi.getServerInfo(url)
            .mapSuccess { ServerInfo(it.version) }
    }
}
