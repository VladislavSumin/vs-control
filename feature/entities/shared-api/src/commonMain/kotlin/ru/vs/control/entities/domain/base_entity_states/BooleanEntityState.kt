package ru.vs.control.entities.domain.base_entity_states

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityState

@Serializable
data class BooleanEntityState(val value: Boolean) : EntityState
