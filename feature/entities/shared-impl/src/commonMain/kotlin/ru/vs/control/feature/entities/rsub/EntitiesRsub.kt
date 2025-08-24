package ru.vs.control.feature.entities.rsub

import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.dto.EntityDto
import ru.vs.control.id.Id
import ru.vs.rsub.RSubInterface

@RSubInterface
interface EntitiesRsub {
    /**
     * Observe all entities states
     */
    fun observeEntities(): Flow<List<EntityDto>>

    /**
     * Try to update state of mutable entity with [id].
     *
     * @param id entity id
     * @param newState new state for mutable entity with [id]
     *
     * @return true if entity updated successfully, false otherwise
     */
    suspend fun updateEntity(serverEntityId: Id.DoubleId, newState: EntityState): Boolean
}
