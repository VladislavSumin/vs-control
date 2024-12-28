package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.control.feature.servers.client.repository.ServerId
import ru.vs.control.feature.servers.client.repository.ServersRepository
import ru.vs.core.coroutines.cachingStateProcessing
import ru.vs.core.coroutines.share

internal interface ServersInteractor {
    suspend fun add(server: Server)
    suspend fun delete(server: Server)
    fun observe(): Flow<List<Server>>

    /**
     * Позволяет получить [ServersInteractor] связанный с конкретным id, а так же инкапсулировать
     * его жизненный цикл внутри данного [Flow]. При удалении сервера вернет новое null значение.
     */
    fun observeServerInteractor(id: ServerId): Flow<ServerInteractor?>
}

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
    private val serverInteractorImplFactory: ServerInteractorImplFactory,
    private val scope: CoroutineScope,
) : ServersInteractor {
    /**
     * Кешируемый список актуальных [ServersInteractor].
     */
    val serverInteractors: SharedFlow<Map<ServerId, ServerInteractorImpl>> = observe()
        .cachingStateProcessing(keySelector = { it.id }) {
            emit(serverInteractorImplFactory.create(it.value.id, it))
        }
        .map { it.associateBy { it.id } }
        .share(scope)

    override suspend fun add(server: Server) = serversRepository.insert(server)
    override suspend fun delete(server: Server) = serversRepository.delete(server)
    override fun observe(): Flow<List<Server>> = serversRepository.observe()

    override fun observeServerInteractor(id: ServerId): Flow<ServerInteractor?> = serverInteractors
        .map { it[id] }
        .distinctUntilChanged()
}
