package ru.vs.control.feature.embeddedServer.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory

/**
 * Отвечает за один встроенный сервис с [embeddedServerId].
 */
@GenerateFactory
internal class EmbeddedServerInteractor(
    // TODO поменять id на value класс
    @ByCreate val embeddedServerId: Long,
    @ByCreate config: StateFlow<EmbeddedServer>,
) {
    suspend fun run() {
        println("QWQW: run server ${embeddedServerId}")
        delay(Long.MAX_VALUE)
    }
}
