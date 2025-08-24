package ru.vs.control.feature.entities.client.domain

import ru.vs.control.id.Id

/**
 * На клиенте id сущности состоит из трех частей <id_сервера>#<id_сервиса>#<id_сущности>
 */
typealias EntityId = Id.TripleId

/**
 * На сервере id сущности состоит из двух частей <id_сервиса>#<id_сущности>
 */
typealias ServerEntityId = Id.DoubleId
