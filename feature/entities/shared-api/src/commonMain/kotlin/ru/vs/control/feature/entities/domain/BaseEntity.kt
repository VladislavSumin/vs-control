package ru.vs.control.feature.entities.domain

/**
 * Base interface for any Entity, contains shared entity part for client and server,
 * and extends by Entity interface on client and server implementations.
 *
 * @param T - primary state type.
 */
interface BaseEntity<T : EntityState> {
    /**
     *  Entity id, must be unique.
     */
    val id: EntityId

    /**
     * Entity primary state (type don't change on entities updates).
     */
    val primaryState: T

    /**
     * Marks can client try to mutate primary state.*
     */
    val isMutable: Boolean

    /**
     * Entity properties collection.
     */
    val properties: EntityProperties
}
