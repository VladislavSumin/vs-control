package ru.vs.core.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import org.kodein.di.DirectDI

interface DatabaseDriverFactory {
    /**
     * Create [SqlDriver] driver for database
     * If database not exist initialize it with given [SqlSchema]
     * TODO добавить сюда передачу пути к библиотеке вместо ее имени.
     */
    suspend fun create(name: String, schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver
}

/**
 * Creates platform specific instance of [DatabaseDriverFactory]
 * [DirectDI] access need to access platform specific objects (android context for example)
 */
internal expect fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory
