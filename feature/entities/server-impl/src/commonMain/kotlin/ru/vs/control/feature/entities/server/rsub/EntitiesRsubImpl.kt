package ru.vs.control.feature.entities.server.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.feature.entities.domain.EntityId
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.dto.EntityDto
import ru.vs.control.feature.entities.dto.toDto
import ru.vs.control.feature.entities.rsub.EntitiesRsub
import ru.vs.control.feature.entities.server.domain.EntitiesInteractor

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
