package ru.vs.control.entities.domain.base_entity_properties

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityProperty

/**
 * Default human-readable name for Entity
 */
@Serializable
data class DefaultNameEntityProperty(val name: String) : EntityProperty
