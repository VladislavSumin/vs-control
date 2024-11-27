package ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen

import kotlinx.serialization.Serializable
import ru.vs.core.navigation.ScreenParams

/**
 * Экран добавления embedded сервера.
 * **Внимание** открывать этот экран можно только если embedded сервер поддерживается текущей платформой.
 * Проверить это можно через [ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServerSupportInteractor].
 */
@Serializable
data object AddEmbeddedServerScreenParams : ScreenParams
