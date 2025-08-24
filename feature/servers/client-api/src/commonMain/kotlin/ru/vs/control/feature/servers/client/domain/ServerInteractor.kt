package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnectionStatus

/**
 * Отвечает за сервер с конкретным [id].
 *
 * Всегда создается ровно один инстанс на каждый id. При изменении другой информации о сервере (например изменение
 * имени) инстанс не пересоздается.
 */
interface ServerInteractor {
    val id: ServerId
    val server: Flow<Server>
    val connectionStatus: Flow<RSubConnectionStatus>

    /**
     * Позволяет выполнить запросы к RSub используя определенный интерфейс [T]
     * ```kotlin
     * withRSub(::SomeRsubImpl) { it.observeSomeState() }
     * ```
     */
    fun <T, V> withRSub(factory: (RSubClient) -> T, block: (T) -> Flow<V>): Flow<V>

    /**
     * Метод аналогичен методу [withRSub], но в случае разрыва соединения с сервером вызывает [onConnectionError], а
     * после восстановления соединения перезапускает [block]. Обрабатываются только ошибки соединения, все прочие ошибки
     * будут выброшены внутри выходного [Flow]
     */
    fun <T, V> withConnectedRSub(
        factory: (RSubClient) -> T,
        onConnectionError: () -> V,
        block: (T) -> Flow<V>,
    ): Flow<V>
}
