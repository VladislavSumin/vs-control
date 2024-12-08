package ru.vs.control.feature.servers.client.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlin.jvm.JvmInline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
        serverQueriesProvider.getServerRecordQueries().insert(server.name, server.accessToken)
        TODO("Not yet implemented")
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

@JvmInline
internal value class ServerId(val raw: Long)

internal data class Server(val id: ServerId, val name: String, val accessToken: String)

private fun Server.toRecord() = ServerRecord(id.raw, name, accessToken)
private fun ServerRecord.toModel() = Server(ServerId(id), name, accessToken)
private fun List<ServerRecord>.toModels() = map { it.toModel() }