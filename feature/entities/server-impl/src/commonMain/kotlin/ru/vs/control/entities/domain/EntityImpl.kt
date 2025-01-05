package ru.vs.control.entities.domain

import ru.vs.control.feature.entities.domain.EntityId
import ru.vs.control.feature.entities.domain.EntityProperties
import ru.vs.control.feature.entities.domain.EntityState

internal data class EntityImpl<T : EntityState>(
    override val id: EntityId,
    override val primaryState: T,
    override val isMutable: Boolean,
    override val properties: EntityProperties = EntityProperties(),
) : Entity<T>
