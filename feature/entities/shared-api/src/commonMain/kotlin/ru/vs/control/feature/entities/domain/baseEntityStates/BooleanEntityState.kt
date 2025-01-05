package ru.vs.control.feature.entities.domain.baseEntityStates

import kotlinx.serialization.Serializable
import ru.vs.control.feature.entities.domain.EntityState

@Serializable
data class BooleanEntityState(val value: Boolean) : EntityState
