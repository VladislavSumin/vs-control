package ru.vs.control.feature.entities.client.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.entities.domain.EntityId

internal interface EntitiesInteractor {
    /**
     * Observe entities from current default server
     */
    fun observeEntities(): Flow<Entities<*>>

    /**
     * Observe entity by [entityId] from current default server
     */
    fun observeEntity(entityId: EntityId): Flow<Entity<*>?>
}

// TODO
@Suppress("UnusedPrivateProperty")
internal class EntitiesInteractorImpl(
    // private val serversConnectionInteractor: ServersConnectionInteractor,
    scope: CoroutineScope,
) : EntitiesInteractor {

    /**
     * Holds connection with current selected server and keep actual entities state
     */
//    private val entitiesFlow: SharedFlow<Entities<*>> =
//        serversConnectionInteractor.observeSelectedServerConnection()
//            .flatMapLatest { connection ->
//                connection?.entities?.observeEntities()
//                    ?.map { it.toEntity(connection.server).associateBy(Entity<*>::id) }
//                    ?: flowOf(emptyMap())
//            }
//            .shareIn(
//                scope = scope,
//                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
//                replay = 1
//            )

    override fun observeEntities(): Flow<Entities<*>> {
        return TODO() // entitiesFlow
    }

    override fun observeEntity(entityId: EntityId): Flow<Entity<*>?> {
        return TODO() // entitiesFlow.map { it[entityId] }
    }
}
