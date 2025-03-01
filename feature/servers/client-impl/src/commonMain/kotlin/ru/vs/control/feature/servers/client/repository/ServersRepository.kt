package ru.vs.control.feature.servers.client.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vs.control.feature.servers.client.domain.Server
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.core.coroutines.DispatchersProvider

internal interface ServersRepository {
    suspend fun insert(server: Server)
    suspend fun delete(server: Server)
    fun observe(): Flow<List<Server>>
}

internal class ServersRepositoryImpl(
    private val serverQueriesProvider: ServerQueriesProvider,
    private val dispatchersProvider: DispatchersProvider,
) : ServersRepository {
    override suspend fun insert(server: Server) = withContext(dispatchersProvider.io) {
        check(server.id.raw == 0L)
        serverQueriesProvider.getServerRecordQueries()
            .insert(server.name, server.isSecure, server.host, server.port.toLong(), server.accessToken)
    }

    override suspend fun delete(server: Server) = withContext(dispatchersProvider.io) {
        check(server.id.raw != 0L)
        serverQueriesProvider.getServerRecordQueries().delete(server.id.raw)
    }

    override fun observe(): Flow<List<Server>> {
        return flow { emit(serverQueriesProvider.getServerRecordQueries()) }
            .flatMapLatest {
                it.selectAll()
                    .asFlow()
                    .mapToList(dispatchersProvider.io)
                    .map { it.toModels() }
            }
    }
}

private fun ServerRecord.toModel() = Server(ServerId(id), name, isSecure, host, port.toInt(), accessToken)
private fun List<ServerRecord>.toModels() = map { it.toModel() }
