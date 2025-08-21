package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow

interface ServersInteractor {
    suspend fun add(server: Server)
    suspend fun delete(server: Server)
    fun observe(): Flow<List<Server>>

    /**
     * Позволяет получить [ServersInteractor] связанный с конкретным id, а так же инкапсулировать
     * его жизненный цикл внутри данного [Flow]. При удалении сервера вернет новое null значение.
     */
    fun observeServerInteractor(id: ServerId): Flow<ServerInteractor?>
}
