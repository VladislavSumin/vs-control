package ru.vs.control.feature.servers.client.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.control.feature.servers.client.repository.ServersRepository

internal interface ServersInteractor {
    suspend fun add(server: Server)
    suspend fun delete(server: Server)
    fun observe(): Flow<List<Server>>
}

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
) : ServersInteractor {
    override suspend fun add(server: Server) = serversRepository.insert(server)
    override suspend fun delete(server: Server) = serversRepository.delete(server)
    override fun observe(): Flow<List<Server>> = serversRepository.observe()
}
