package ru.vs.control.feature.entities.server.domain

import ru.vs.control.feature.entities.domain.BaseEntity
import ru.vs.control.feature.entities.domain.EntityState

/**
 * Primary interface for server entity
 * @param T - primary state type
 */
interface Entity<T : EntityState> : BaseEntity<EntityId, T>

typealias Entities<T> = Map<EntityId, Entity<T>>
