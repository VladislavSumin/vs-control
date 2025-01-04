package ru.vs.control.entities.domain

import ru.vs.control.feature.servers.client.domain.Server

/**
 * Primary class for client entity.
 *
 * @param server server who manages this entity.
 * @param id entity id, must be unique.
 * @param primaryState entity primary state (type don't change on entities updates).
 * @param isMutable is allowed to mutate this entity.
 * @param T primary state type.
 */
data class Entity<T : EntityState>(
    val server: Server,
    override val id: EntityId,
    override val primaryState: T,
    override val isMutable: Boolean,
    override val properties: EntityProperties,
) : BaseEntity<T>

typealias Entities<T> = Map<EntityId, Entity<T>>
