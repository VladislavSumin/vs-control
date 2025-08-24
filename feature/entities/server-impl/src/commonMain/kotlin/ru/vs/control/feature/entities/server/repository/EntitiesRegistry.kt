package ru.vs.control.feature.entities.server.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.server.domain.Entities
import ru.vs.control.feature.entities.server.domain.Entity
import ru.vs.control.feature.entities.server.domain.EntityId
import ru.vs.control.feature.entities.server.domain.EntityImpl

internal interface EntitiesRegistry {
    fun observeEntities(): Flow<Entities<*>>

    /**
     * Holds entity with given [Entity.id] while [block] is running. When exits from [block] remove entity from registry
     *
     * [update] - update function to safely update current entity
     * Attention! Update function may update only one entity (id must be equal [initialValue] id)
     * Attention! [Entity.primaryState] must be same as [initialValue]
     *
     * @param initialValue - initial entity state
     * @param block - block running at caller coroutine context
     */
    suspend fun <T : EntityState> holdEntity(
        initialValue: EntityImpl<T>,
        block: suspend (
            update: suspend (
                (entity: Entity<T>) -> T,
            ) -> Unit,
        ) -> Unit,
    )
}

internal class EntitiesRegistryImpl : EntitiesRegistry {
    private val storage = EntitiesStorage()

    override fun observeEntities(): Flow<Entities<*>> {
        return storage.entities
    }

    override suspend fun <T : EntityState> holdEntity(
        initialValue: EntityImpl<T>,
        block: suspend (update: suspend ((entity: Entity<T>) -> T) -> Unit) -> Unit,
    ) {
        val id = initialValue.id
        var currentEntity: EntityImpl<T> = initialValue

        storage.update { entities ->
            if (entities.containsKey(id)) error("Entity with ${initialValue.id} already exist")
            entities[id] = initialValue
        }

        try {
            block { updateFunction ->

                val newState = updateFunction(currentEntity)
                val newValue = currentEntity.copy(primaryState = newState)

                storage.update { it[id] = newValue }
                currentEntity = newValue
            }
        } finally {
            withContext(NonCancellable) {
                storage.update { it.remove(id) }
            }
        }
    }

    /**
     * Given safely access to internal storage collection
     */
    private class EntitiesStorage {
        private val entitiesMut = MutableStateFlow(mapOf<EntityId, Entity<*>>())
        private val lock = Mutex()

        val entities: StateFlow<Map<EntityId, Entity<*>>> = entitiesMut

        suspend fun update(block: (entities: MutableMap<EntityId, Entity<*>>) -> Unit) {
            lock.withLock {
                val newData = entitiesMut.value.toMutableMap()
                block(newData)
                entitiesMut.value = newData
            }
        }
    }
}
