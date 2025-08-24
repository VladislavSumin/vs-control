package ru.vs.control.feature.entities.server.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.entities.domain.EntityProperties
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.server.repository.EntitiesRegistry

internal class EntitiesInteractorImpl(
    private val entitiesRegistry: EntitiesRegistry,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<Entities<*>> {
        return entitiesRegistry.observeEntities()
    }

    override suspend fun <T : EntityState> holdEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties,
        block: suspend (update: suspend ((entity: Entity<T>) -> T) -> Unit) -> Unit,
    ) = holdEntityInternal(
        id = id,
        primaryState = primaryState,
        isMutable = false,
        properties = properties,
        block = block,
    )

    override suspend fun <T : EntityState> holdMutableEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties,
        onUserRequestUpdatePrimaryState: (requestedPrimaryState: T) -> Boolean,
        block: suspend (update: suspend ((entity: Entity<T>) -> T) -> Unit) -> Unit,
    ) {
        // TODO implement onUserRequestUpdatePrimaryState block

        holdEntityInternal(
            id = id,
            primaryState = primaryState,
            isMutable = true,
            properties = properties,
            block = block,
        )
    }

    override suspend fun <T : EntityState> holdConstantEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties,
    ): Nothing {
        holdEntityInternal(
            id = id,
            primaryState = primaryState,
            isMutable = false,
            properties = properties,
        ) { delay(Long.MAX_VALUE) }
        error("unreachable code")
    }

    private suspend fun <T : EntityState> holdEntityInternal(
        id: EntityId,
        primaryState: T,
        isMutable: Boolean,
        properties: EntityProperties,
        block: suspend (update: suspend ((entity: Entity<T>) -> T) -> Unit) -> Unit,
    ) {
        val entity = EntityImpl(
            id = id,
            primaryState = primaryState,
            isMutable = isMutable,
            properties = properties,
        )
        entitiesRegistry.holdEntity(entity, block)
    }

    override suspend fun updateEntity(id: EntityId, newState: EntityState): Boolean {
        TODO("Not yet implemented")
    }
}
