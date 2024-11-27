package ru.vs.control.feature.embeddedServer.client.service

import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServerRecordQueries

interface EmbeddedServerQueriesProvider {
    suspend fun getEmbeddedServerRecordQueries(): EmbeddedServerRecordQueries
}
