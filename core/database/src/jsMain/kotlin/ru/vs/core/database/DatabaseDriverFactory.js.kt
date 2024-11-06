package ru.vs.core.database

// import app.cash.sqldelight.driver.sqljs.initSqlDriver
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import org.kodein.di.DirectDI

private class JsDatabaseDriverFactory : DatabaseDriverFactory {
    override suspend fun create(name: String, schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        // return initSqlDriver(schema).await()
        TODO()
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return JsDatabaseDriverFactory()
}
