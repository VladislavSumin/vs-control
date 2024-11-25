package ru.vs.control.feature.serverInfo

import kotlinx.serialization.Serializable

/**
 * Базовая информация о сервера.
 *
 * @param version версия сервера.
 */
@Serializable
data class ServerInfo(
    val version: String,
)
