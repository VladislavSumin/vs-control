package ru.vs.control.entities.domain.baseEntityProperties

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityProperty

/**
 * Default human-readable name for Entity
 */
@Serializable
data class DefaultNameEntityProperty(val name: String) : EntityProperty
