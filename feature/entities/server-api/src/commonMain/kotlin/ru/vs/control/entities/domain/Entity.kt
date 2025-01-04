package ru.vs.control.entities.domain

/**
 * Primary interface for server entity
 * @param T - primary state type
 */
interface Entity<T : EntityState> : BaseEntity<T>

typealias Entities<T> = Map<EntityId, Entity<T>>
