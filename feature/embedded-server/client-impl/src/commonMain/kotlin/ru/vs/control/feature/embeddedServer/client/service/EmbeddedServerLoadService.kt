package ru.vs.control.feature.embeddedServer.client.service

import kotlinx.coroutines.flow.collect
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServersInteractor
import ru.vs.core.autoload.AutoloadService
import ru.vs.core.coroutines.cachingStateProcessing

/**
 * Сервис отвечающий за автоматический запуск встроенных серверов при старте приложения
 */
internal class EmbeddedServerLoadService(
    private val embeddedServersInteractor: EmbeddedServersInteractor,
) : AutoloadService {
    override suspend fun run() {
        embeddedServersInteractor
            .observeEmbeddedServerInteractors()
            .cachingStateProcessing(keySelector = { it.id }) {
                it.value.run()
                emit(Unit)
            }
            .collect()
    }
}
