package ru.vs.control.feature.embeddedServer.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepository
import ru.vs.core.coroutines.cachingStateProcessing
import ru.vs.core.coroutines.share

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

    /**
     * Подписывается на список [EmbeddedServerInteractor] соответствующий списку добавленных серверов.
     */
    fun observeEmbeddedServerInteractors(): Flow<List<EmbeddedServerInteractor>>
}

internal class EmbeddedServersInteractorImpl(
    private val embeddedServerInteractorFactory: EmbeddedServerInteractorFactory,
    private val embeddedServersRepository: EmbeddedServersRepository,
    scope: CoroutineScope,
) : EmbeddedServersInteractor {

    /**
     * Важно что бы для одного сервера не создавалось несколько интеракторов, поэтому создаем их только в этом месте
     * и сохраняем состояние для всех подписчиков.
     */
    private val embeddedServerInteractors = observeEmbeddedServers()
        .cachingStateProcessing(keySelector = { it.id }) {
            emit(embeddedServerInteractorFactory.create(it.value.id, it))
        }
        .share(scope)

    override suspend fun add(server: EmbeddedServer) = embeddedServersRepository.insert(server)
    override suspend fun delete(server: EmbeddedServer) = embeddedServersRepository.delete(server)

    override fun observeEmbeddedServers(): Flow<List<EmbeddedServer>> = embeddedServersRepository.observe()
    override fun observeEmbeddedServerInteractors(): Flow<List<EmbeddedServerInteractor>> = embeddedServerInteractors
}
