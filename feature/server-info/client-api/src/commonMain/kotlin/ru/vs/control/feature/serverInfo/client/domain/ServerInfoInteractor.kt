package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url

interface ServerInfoInteractor {
    suspend fun getServerInfo(url: Url): ServerInfo
}
