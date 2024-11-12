package ru.vs.control.feature.embeddedServer.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServerId
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory

/**
 * Отвечает за один встроенный сервис с [id].
 */
@GenerateFactory
internal class EmbeddedServerInteractor(
    @ByCreate val id: EmbeddedServerId,
    @ByCreate config: StateFlow<EmbeddedServer>,
) {
    suspend fun run() {
        delay(Long.MAX_VALUE)
    }
}
