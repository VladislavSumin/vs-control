package ru.vs.control.entities.dto

import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityProperties
import ru.vs.control.servers.domain.Server

internal fun EntityDto.toEntity(server: Server) = Entity(
    server = server,
    id = id,
    primaryState = primaryState,
    isMutable = isMutable,
    properties = EntityProperties(properties),
)

internal fun Collection<EntityDto>.toEntity(server: Server) = map { it.toEntity(server) }
