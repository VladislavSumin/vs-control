package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApi

internal class ServerInfoInteractorImpl(
    private val serverInfoApi: ServerInfoApi,
) : ServerInfoInteractor {
    override suspend fun getServerInfo(url: Url): ServerInfo {
        val info = serverInfoApi.getServerInfo(url)
        return ServerInfo(info.version)
    }
}
