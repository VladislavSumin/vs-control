package ru.vs.control.feature.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.feature.entities.domain.EntityProperty
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.id.Id

@Serializable
data class EntityDto(
    val id: Id.DoubleId,
    val primaryState: EntityState,
    val isMutable: Boolean,
    val properties: Collection<EntityProperty>,
)
