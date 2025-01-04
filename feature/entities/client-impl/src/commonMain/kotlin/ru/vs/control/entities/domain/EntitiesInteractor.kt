package ru.vs.control.entities.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ru.vs.control.entities.dto.toEntity

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
        return TODO()//entitiesFlow
    }

    override fun observeEntity(entityId: EntityId): Flow<Entity<*>?> {
        return TODO()// entitiesFlow.map { it[entityId] }
    }
}
