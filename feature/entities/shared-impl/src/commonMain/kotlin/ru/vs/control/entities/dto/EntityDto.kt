package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.BaseEntity
import ru.vs.control.entities.domain.EntityId
import ru.vs.control.entities.domain.EntityProperty
import ru.vs.control.entities.domain.EntityState

@Serializable
data class EntityDto(
    val id: EntityId,
    val primaryState: EntityState,
    val isMutable: Boolean,
    val properties: Collection<EntityProperty>
)

fun BaseEntity<*>.toDto() = EntityDto(
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = properties.raw,
)

fun Collection<BaseEntity<*>>.toDto() = map { it.toDto() }
