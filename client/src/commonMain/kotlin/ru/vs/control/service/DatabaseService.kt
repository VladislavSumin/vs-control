package ru.vs.control.service

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServerQueriesProvider
import ru.vs.control.repository.Database
import ru.vs.core.database.AbstractDatabaseService
import ru.vs.core.database.DatabaseDriverFactory

class DatabaseService(factory: DatabaseDriverFactory) :
    AbstractDatabaseService<Database>(factory),
    EmbeddedServerQueriesProvider {
    override val schema: SqlSchema<QueryResult.Value<Unit>> = Database.Schema
    override fun createDatabaseFromDriver(driver: SqlDriver): Database = Database(driver)

    override suspend fun getEmbeddedServerRecordQueries() = getDatabase().embeddedServerRecordQueries
}
