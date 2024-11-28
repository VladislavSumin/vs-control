package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import ru.vs.core.ktor.client.SafeResponse

interface ServerInfoInteractor {
    suspend fun getServerInfo(url: Url): SafeResponse<ServerInfo>
}
