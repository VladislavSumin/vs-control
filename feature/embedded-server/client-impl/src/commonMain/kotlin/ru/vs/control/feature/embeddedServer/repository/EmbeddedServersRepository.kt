package ru.vs.control.feature.embeddedServer.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlin.jvm.JvmInline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vs.control.feature.embeddedServer.service.EmbeddedServerQueriesProvider
import ru.vs.core.coroutines.DispatchersProvider

internal interface EmbeddedServersRepository {
    suspend fun insert(server: EmbeddedServer)
    suspend fun delete(server: EmbeddedServer)
    fun observe(): Flow<List<EmbeddedServer>>
}

internal class EmbeddedServersRepositoryImpl(
    private val queriesProvider: EmbeddedServerQueriesProvider,
    private val dispatchersProvider: DispatchersProvider,
) : EmbeddedServersRepository {
    override suspend fun insert(server: EmbeddedServer) = withContext(dispatchersProvider.default) {
        check(server.id.raw == 0L)
        queriesProvider.getEmbeddedServerRecordQueries().insert(server.toRecord())
    }

    override suspend fun delete(server: EmbeddedServer) = withContext(dispatchersProvider.default) {
        check(server.id.raw != 0L)
        queriesProvider.getEmbeddedServerRecordQueries().delete(server.id.raw)
    }

    override fun observe(): Flow<List<EmbeddedServer>> {
        return flow { emit(queriesProvider.getEmbeddedServerRecordQueries()) }
            .flatMapLatest {
                it.selectAll()
                    .asFlow()
                    .mapToList(dispatchersProvider.default)
                    .map { it.toModels() }
            }
    }
}

@JvmInline
value class EmbeddedServerId(val raw: Long)

data class EmbeddedServer(val id: EmbeddedServerId = EmbeddedServerId(0), val name: String)

// TODO попробовать сделать генерацию этих методов.
fun EmbeddedServer.toRecord() = EmbeddedServerRecord(id.raw, name)
fun EmbeddedServerRecord.toModel() = EmbeddedServer(EmbeddedServerId(id), name)
fun List<EmbeddedServerRecord>.toModels() = map { it.toModel() }
