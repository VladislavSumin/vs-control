package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.control.feature.servers.client.repository.ServerId
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory

/**
 * Отвечает за сервер с конкретным [id].
 * Всегда создается ровно один инстанс на каждый id. При изменении другой информации о сервере не пересоздается
 */
internal interface ServerInteractor {
    val id: ServerId
    val server: Flow<Server>
}

@GenerateFactory
internal class ServerInteractorImpl(
    @ByCreate override val id: ServerId,
    @ByCreate override val server: StateFlow<Server>,
) : ServerInteractor
