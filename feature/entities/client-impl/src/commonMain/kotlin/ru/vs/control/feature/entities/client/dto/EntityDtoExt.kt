package ru.vs.control.feature.entities.client.dto

import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.domain.EntityProperties
import ru.vs.control.feature.entities.dto.EntityDto
import ru.vs.control.feature.servers.client.domain.ServerId

internal fun EntityDto.toEntity(serverId: ServerId) = Entity(
    serverId = serverId,
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = EntityProperties(properties),
)

internal fun Collection<EntityDto>.toEntity(serverId: ServerId) = map { it.toEntity(serverId) }
