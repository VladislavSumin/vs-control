package ru.vs.control.feature.serverInfo.client.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import ru.vs.control.feature.serverInfo.ServerInfo

internal interface ServerInfoApi {
    suspend fun getServerInfo(url: Url): ServerInfo
}

internal class ServerInfoApiImpl(
    private val client: HttpClient,
) : ServerInfoApi {
    override suspend fun getServerInfo(url: Url): ServerInfo {
        return client.get(url).body()
    }
}
