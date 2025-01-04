package ru.vs.control.entities.domain

internal data class EntityImpl<T : EntityState>(
    override val id: EntityId,
    override val primaryState: T,
    override val isMutable: Boolean,
    override val properties: EntityProperties = EntityProperties(),
) : Entity<T>
