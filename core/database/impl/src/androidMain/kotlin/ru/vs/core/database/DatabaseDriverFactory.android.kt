package ru.vs.core.database

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.kodein.di.DirectDI
import org.kodein.di.instance

private class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override suspend fun create(name: String, schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        TODO("Not correctly implemented")
        return AndroidSqliteDriver(schema, context, "$name.db")
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return AndroidDatabaseDriverFactory(instance())
}
