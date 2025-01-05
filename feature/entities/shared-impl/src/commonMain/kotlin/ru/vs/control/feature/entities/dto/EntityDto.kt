package ru.vs.control.feature.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.feature.entities.domain.BaseEntity
import ru.vs.control.feature.entities.domain.EntityId
import ru.vs.control.feature.entities.domain.EntityProperty
import ru.vs.control.feature.entities.domain.EntityState

@Serializable
data class EntityDto(
    val id: EntityId,
    val primaryState: EntityState,
    val isMutable: Boolean,
    val properties: Collection<EntityProperty>,
)

fun BaseEntity<*>.toDto() = EntityDto(
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = properties.raw,
)

fun Collection<BaseEntity<*>>.toDto() = map { it.toDto() }
