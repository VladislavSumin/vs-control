package ru.vs.control.feature.entities.client.dto

import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.domain.EntityProperties
import ru.vs.control.feature.entities.dto.EntityDto
import ru.vs.control.feature.servers.client.domain.Server

internal fun EntityDto.toEntity(server: Server) = Entity(
    server = server,
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = EntityProperties(properties),
)

internal fun Collection<EntityDto>.toEntity(server: Server) = map { it.toEntity(server) }
