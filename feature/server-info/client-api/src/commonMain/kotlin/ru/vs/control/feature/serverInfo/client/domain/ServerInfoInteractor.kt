package ru.vs.control.feature.serverInfo.client.domain

import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.core.ktor.client.SafeResponse

interface ServerInfoInteractor {
    /**
     * Разово получает информацию о сервере по [url]. Этот метод нужен только для получения информации о сервере при
     * первичной настройке подключения.
     */
    suspend fun getInitialServerInfo(url: Url): SafeResponse<ServerInfo>

    /**
     * Подписывается на информацию о сервере по [serverId].
     */
    fun observeServerInfo(serverId: ServerId): Flow<ServerInfo>

    fun observeConnectionStatusWithServerInfo(serverId: ServerId): Flow<ConnectionStatusWithServerInfo>

    sealed interface ConnectionStatusWithServerInfo {
        data object Connecting : ConnectionStatusWithServerInfo
        data class Connected(val serverInfo: ServerInfo) : ConnectionStatusWithServerInfo
        data class FailedToGetServerInfo(val error: Throwable) : ConnectionStatusWithServerInfo
        data class Reconnecting(val connectionError: Exception) : ConnectionStatusWithServerInfo
    }
}
