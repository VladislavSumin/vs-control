package ru.vs.control.feature.entities.server.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.dto.EntityDto
import ru.vs.control.feature.entities.rsub.EntitiesRsub
import ru.vs.control.feature.entities.server.domain.EntitiesInteractor
import ru.vs.control.feature.entities.server.domain.Entity
import ru.vs.control.feature.entities.server.domain.EntityId

internal class EntitiesRsubImpl(
    private val entitiesInteractor: EntitiesInteractor,
) : EntitiesRsub {
    override fun observeEntities(): Flow<List<EntityDto>> {
        return entitiesInteractor.observeEntities().map { it.values.toDto() }
    }

    override suspend fun updateEntity(id: EntityId, newState: EntityState): Boolean {
        return entitiesInteractor.updateEntity(
            id = id,
            newState = newState,
        )
    }
}

private fun Entity<*>.toDto() = EntityDto(
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = properties.raw,
)

private fun Collection<Entity<*>>.toDto() = map { it.toDto() }
