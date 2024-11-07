package ru.vs.control.feature.embeddedServer.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vs.control.feature.embeddedServer.service.EmbeddedServerQueriesProvider

internal interface EmbeddedServersRepository {
    suspend fun insert(server: EmbeddedServer)
}

internal class EmbeddedServersRepositoryImpl(
    private val queriesProvider: EmbeddedServerQueriesProvider,
) : EmbeddedServersRepository {
    override suspend fun insert(server: EmbeddedServer) = withContext(Dispatchers.Default) {
        check(server.id == 0L)
        queriesProvider.getEmbeddedServerRecordQueries().insert(server.toRecord())
    }
}

data class EmbeddedServer(val id: Long = 0, val name: String)

// TODO попробовать сделать генерацию этих методов.
fun EmbeddedServer.toRecord() = EmbeddedServerRecord(id, name)
fun EmbeddedServerRecord.toModel() = EmbeddedServer(id, name)
