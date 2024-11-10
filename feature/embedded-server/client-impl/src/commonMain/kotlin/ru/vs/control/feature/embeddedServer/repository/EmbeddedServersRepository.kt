package ru.vs.control.feature.embeddedServer.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vs.control.feature.embeddedServer.service.EmbeddedServerQueriesProvider

internal interface EmbeddedServersRepository {
    suspend fun insert(server: EmbeddedServer)
    suspend fun delete(server: EmbeddedServer)
    fun observe(): Flow<List<EmbeddedServer>>
}

internal class EmbeddedServersRepositoryImpl(
    private val queriesProvider: EmbeddedServerQueriesProvider,
) : EmbeddedServersRepository {
    // TODO пропробсить сюда кастомные диспатчеры?
    override suspend fun insert(server: EmbeddedServer) = withContext(Dispatchers.Default) {
        check(server.id == 0L)
        queriesProvider.getEmbeddedServerRecordQueries().insert(server.toRecord())
    }

    override suspend fun delete(server: EmbeddedServer) = withContext(Dispatchers.Default) {
        check(server.id != 0L)
        queriesProvider.getEmbeddedServerRecordQueries().delete(server.id)
    }

    // TODO пропробсить сюда кастомные диспатчеры?
    override fun observe(): Flow<List<EmbeddedServer>> {
        return flow { emit(queriesProvider.getEmbeddedServerRecordQueries()) }
            .flatMapLatest {
                it.selectAll()
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { it.toModels() }
            }
    }
}

data class EmbeddedServer(val id: Long = 0, val name: String)

// TODO попробовать сделать генерацию этих методов.
fun EmbeddedServer.toRecord() = EmbeddedServerRecord(id, name)
fun EmbeddedServerRecord.toModel() = EmbeddedServer(id, name)
fun List<EmbeddedServerRecord>.toModels() = map { it.toModel() }
