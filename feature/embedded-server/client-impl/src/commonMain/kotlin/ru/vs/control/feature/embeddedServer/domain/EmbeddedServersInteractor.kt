package ru.vs.control.feature.embeddedServer.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepository

/**
 * Взаимодействует с добавленными встроенными серверами.
 */
internal interface EmbeddedServersInteractor {
    /**
     * Добавляет новый встроенный сервер.
     */
    suspend fun add(server: EmbeddedServer)

    /**
     * Останавливает и удаляет встроенный сервер.
     * Удаление происходит вместе со всей дополнительной информацией используемой сервером, будь то конфиги или базы
     * данных встроенного сервера.
     */
    suspend fun delete(server: EmbeddedServer)

    /**
     * Подписывается на список всех добавленных встроенных серверов.
     */
    fun observeEmbeddedServers(): Flow<List<EmbeddedServer>>
}

internal class EmbeddedServersInteractorImpl(
    private val embeddedServersRepository: EmbeddedServersRepository,
) : EmbeddedServersInteractor {
    override suspend fun add(server: EmbeddedServer) = embeddedServersRepository.insert(server)
    override suspend fun delete(server: EmbeddedServer) = embeddedServersRepository.delete(server)

    override fun observeEmbeddedServers(): Flow<List<EmbeddedServer>> = embeddedServersRepository.observe()
}
