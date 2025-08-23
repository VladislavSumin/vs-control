package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow

interface ServersInteractor {
    suspend fun add(server: Server)
    suspend fun delete(server: Server)
    fun observe(): Flow<List<Server>>

    /**
     * Позволяет получить [ServerInteractor] для всех добавленных серверов.
     */
    fun observeServerInteractors(): Flow<List<ServerInteractor>>

    /**
     * Позволяет получить [ServersInteractor] связанный с конкретным id, а так же инкапсулировать
     * его жизненный цикл внутри [block]. При удалении сервера вернет новое null значение.
     */
    fun <T> withServerInteractor(id: ServerId, block: ServerInteractor.() -> Flow<T>): Flow<T?>
}
