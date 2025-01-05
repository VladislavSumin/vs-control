package ru.vs.control.feature.entities.domain

import kotlinx.serialization.Serializable

/**
 * Primary entity state, see [BaseEntity.primaryState].
 *
 * Implementation of this interface must be [Serializable] and registered with special way,
 * see [ExternalEntityStateSerializer].
 */
interface EntityState
