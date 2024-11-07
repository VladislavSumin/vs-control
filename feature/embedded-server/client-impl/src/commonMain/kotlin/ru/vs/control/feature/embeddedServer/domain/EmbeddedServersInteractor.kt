package ru.vs.control.feature.embeddedServer.domain

import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepository

internal interface EmbeddedServersInteractor {
    suspend fun add(server: EmbeddedServer)
}

internal class EmbeddedServersInteractorImpl(
    private val embeddedServersRepository: EmbeddedServersRepository,
) : EmbeddedServersInteractor {
    override suspend fun add(server: EmbeddedServer) {
        embeddedServersRepository.insert(server)
    }
}
