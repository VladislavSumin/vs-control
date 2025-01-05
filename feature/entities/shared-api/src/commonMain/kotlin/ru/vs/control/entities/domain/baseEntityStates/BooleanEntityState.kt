package ru.vs.control.entities.domain.baseEntityStates

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityState

@Serializable
data class BooleanEntityState(val value: Boolean) : EntityState
