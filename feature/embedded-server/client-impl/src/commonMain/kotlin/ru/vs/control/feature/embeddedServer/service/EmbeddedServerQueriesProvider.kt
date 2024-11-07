package ru.vs.control.feature.embeddedServer.service

import ru.vs.control.feature.embeddedServer.repository.EmbeddedServerRecordQueries

interface EmbeddedServerQueriesProvider {
    suspend fun getEmbeddedServerRecordQueries(): EmbeddedServerRecordQueries
}
