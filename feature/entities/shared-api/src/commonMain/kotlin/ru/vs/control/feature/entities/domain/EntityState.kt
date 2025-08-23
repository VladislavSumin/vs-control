package ru.vs.control.feature.entities.domain

import kotlinx.serialization.Serializable

/**
 * Primary entity state, see [BaseEntity.primaryState].
 *
 * Implementation of this interface must be [Serializable] and registered with special way,
 * see [ExternalEntityStateSerializer].
 */
interface EntityState

/**
 * Marks "composite" entities.
 *
 * Composite entities is aggregation of some other simple or composite entities to provide
 * complex entity with more than one state
 */
interface CompositeEntityState : EntityState
