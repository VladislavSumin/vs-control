package ru.vs.control.feature.serverInfo

import kotlinx.serialization.Serializable

/**
 * Базовая информация о сервера.
 *
 * @param name имя сервера (задается самим сервером).
 * @param version версия сервера.
 */
@Serializable
data class ServerInfo(
    val name: String,
    val version: String,
)
