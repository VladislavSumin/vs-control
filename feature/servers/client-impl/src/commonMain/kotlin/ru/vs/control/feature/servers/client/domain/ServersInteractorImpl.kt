package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.servers.client.repository.ServersRepository
import ru.vs.core.coroutines.cachingStateProcessing
import ru.vs.core.coroutines.share

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
    private val serverInteractorImplFactory: ServerInteractorImplFactory,
    scope: CoroutineScope,
) : ServersInteractor {
    /**
     * Кешируемый список актуальных [ServersInteractor].
     */
    val serverInteractors: SharedFlow<Map<ServerId, ServerInteractorImpl>> = observe()
        .cachingStateProcessing(keySelector = { it.id }) { serverFlow ->
            emit(serverInteractorImplFactory.create(serverFlow.value.id, serverFlow))
        }
        .map { interactors -> interactors.associateBy { it.id } }
        .share(scope)

    override suspend fun add(server: Server) = serversRepository.insert(server)
    override suspend fun delete(server: Server) = serversRepository.delete(server)
    override fun observe(): Flow<List<Server>> = serversRepository.observe()

    override fun observeServerInteractors(): Flow<List<ServerInteractor>> = serverInteractors.map { it.values.toList() }

    override fun <T> withServerInteractor(
        id: ServerId,
        block: ServerInteractor.() -> Flow<T>,
    ): Flow<T?> = observeServerInteractor(id).flatMapLatest { interactor ->
        interactor?.block() ?: flowOf(null)
    }

    private fun observeServerInteractor(id: ServerId): Flow<ServerInteractor?> = serverInteractors
        .map { it[id] }
        .distinctUntilChanged()
}
