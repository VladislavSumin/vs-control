package ru.vs.control.feature.entities.server.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import ru.vs.control.feature.entities.domain.EntityProperties
import ru.vs.control.feature.entities.domain.EntityState

interface EntitiesInteractor {
    /**
     * Observe state for all entities
     */
    fun observeEntities(): Flow<Entities<*>>

    /**
     * Holds entity with given [id] while [block] is running. When exits from [block] remove entity from registry.
     *
     * [update] update function to safely update current entity state.
     *
     * @param id entity id.
     * @param primaryState entity primary state.
     * @param properties entity properties.
     * @param block block running at caller coroutine context.
     */
    suspend fun <T : EntityState> holdEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties = EntityProperties(),
        block: suspend (
            update: suspend (
                (entity: Entity<T>) -> T,
            ) -> Unit,
        ) -> Unit,
    )

    /**
     * Holds entity with given [id] while [block] is running. When exits from [block] remove entity from registry.
     * Allows clients to mutate entity primary state.
     *
     * [update] update function to safely update current entity state.
     *
     * @param id entity id.
     * @param primaryState entity primary state.
     * @param properties entity properties.
     * @param onUserRequestUpdatePrimaryState callback to receive new state requested by client.
     * @param block block running at caller coroutine context.
     */
    suspend fun <T : EntityState> holdMutableEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties = EntityProperties(),
        onUserRequestUpdatePrimaryState: (requestedPrimaryState: T) -> Boolean,
        block: suspend (
            update: suspend (
                (entity: Entity<T>) -> T,
            ) -> Unit,
        ) -> Unit,
    )

    /**
     * Holds entity with given [id] while called coroutine is running.
     * When cancel called coroutine scope remove entity from registry.
     *
     * @param id entity id.
     * @param primaryState entity primary state.
     * @param properties entity properties.
     */
    suspend fun <T : EntityState> holdConstantEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties = EntityProperties(),
    ): Nothing

    /**
     * Try to update state of mutable entity with [id].
     *
     * @param id entity id
     * @param newState new state for mutable entity with [id]
     *
     * @return true if entity updated successfully, false otherwise
     */
    suspend fun updateEntity(id: EntityId, newState: EntityState): Boolean
}
