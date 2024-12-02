package ru.vs.core.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.kodein.di.DirectDI
import java.io.File

private class JvmDatabaseDriverFactory : DatabaseDriverFactory {
    override suspend fun create(name: String, schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        // TODO add normal schema check
        val isDbExists = File("$name.db").exists()
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:database.db")
        if (!isDbExists) {
            schema.create(driver)
        }
        return driver
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return JvmDatabaseDriverFactory()
}
