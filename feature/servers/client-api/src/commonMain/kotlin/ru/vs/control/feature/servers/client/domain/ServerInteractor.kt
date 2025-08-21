package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow

/**
 * Отвечает за сервер с конкретным [id].
 *
 * Всегда создается ровно один инстанс на каждый id. При изменении другой информации о сервере (например изменение
 * имени) инстанс не пересоздается.
 */
interface ServerInteractor {
    val id: ServerId
    val server: Flow<Server>
    val connectionStatus: Flow<ConnectionStatus>

    sealed interface ConnectionStatus {
        data object Connecting : ConnectionStatus
        data object Connected : ConnectionStatus
        data class Reconnecting(val connectionError: Exception) : ConnectionStatus
    }
}
