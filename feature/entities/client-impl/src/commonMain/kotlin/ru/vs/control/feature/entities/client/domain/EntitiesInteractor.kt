package ru.vs.control.feature.entities.client.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import ru.vs.control.feature.entities.client.dto.toEntity
import ru.vs.control.feature.entities.rsub.EntitiesRsubRSubImpl
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.control.feature.servers.client.domain.ServerInteractor
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.core.coroutines.cachingStateProcessing

internal interface EntitiesInteractor {
    /**
     * Слушает все сущности со всех добавленных серверов.
     */
    fun observeEntities(): Flow<Entities<*>>

    /**
     * Observe entity by [entityId] from current default server
     */
    // fun observeEntity(entityId: EntityId): Flow<Entity<*>?>
}

@Suppress("UnusedPrivateProperty")
internal class EntitiesInteractorImpl(
    private val serversInteractor: ServersInteractor,
    private val scope: CoroutineScope,
) : EntitiesInteractor {

    /**
     * Список всех серверов и соответствущих им потоков сущностей. Потоки сущностей являются кеширующими.
     */
    private val allServersEntities: SharedFlow<Map<ServerId, Flow<Entities<*>>>> =
        serversInteractor.observeServerInteractors()
            .cachingStateProcessing(keySelector = { it.id }) { serverInteractorFlow ->
                serverInteractorFlow.mapLatest { serverInteractor ->
                    serverInteractor.id to serverInteractor.createEntitiesFlow()
                }.collect(this)
            }
            .map { it.associate { (id, flow) -> id to flow } }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
                replay = 1,
            )

    /**
     * Кеширующий поток всех сущностей на всех серверах.
     */
    private val allEntities: SharedFlow<Entities<*>> = allServersEntities
        .flatMapLatest { allServersEntities ->
            combine(allServersEntities.values) {
                // TODO id сущностей на клиенте должен включать id сервера, иначе возможны коллизии при объединении.
                val map = mutableMapOf<EntityId, Entity<*>>()
                it.forEach { map.putAll(it) }
                map
            }
        }
        .shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
            replay = 1,
        )

    /**
     * Создает кеширующий [Flow] всех [Entities] для указанного [ServersInteractor]
     */
    private fun ServerInteractor.createEntitiesFlow(): SharedFlow<Entities<*>> {
        return withRSub(::EntitiesRsubRSubImpl) { entitiesRsub ->
            entitiesRsub
                .observeEntities()
                .map {
                    it.toEntity(this.id)
                        .associateBy(Entity<*>::id)
                }
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
            replay = 1,
        )
    }

    override fun observeEntities(): Flow<Entities<*>> = allEntities

//    override fun observeEntity(entityId: EntityId): Flow<Entity<*>?> {
//        return TODO() // entitiesFlow.map { it[entityId] }
//    }
}
