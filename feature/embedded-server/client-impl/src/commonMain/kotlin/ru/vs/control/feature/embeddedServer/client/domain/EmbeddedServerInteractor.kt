package ru.vs.control.feature.embeddedServer.client.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServer
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServerId

/**
 * Отвечает за один встроенный сервис с [id].
 */
@GenerateFactory
internal class EmbeddedServerInteractor(
    @ByCreate val id: EmbeddedServerId,
    @ByCreate private val config: StateFlow<EmbeddedServer>,
) {
    suspend fun run() {
        config
        delay(Long.MAX_VALUE)
    }
}
