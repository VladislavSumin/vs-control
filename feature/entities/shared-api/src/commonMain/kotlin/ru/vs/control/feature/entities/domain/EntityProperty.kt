package ru.vs.control.feature.entities.domain

import kotlinx.serialization.Serializable

/**
 * Additional entity property, see [BaseEntity.properties].
 *
 * Implementation of this interface must be [Serializable] and registered with special way,
 * see [ExternalEntityPropertySerializer].
 */
interface EntityProperty
