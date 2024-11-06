package ru.vs.core.database

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class AbstractDatabaseService<DATABASE : Transacter>(
    private val factory: DatabaseDriverFactory,
    private val name: String = "database",
) {
    private val lock = Mutex()

    private var database: DATABASE? = null

    /**
     * Create [DATABASE] with given [driver]
     */
    protected abstract fun createDatabaseFromDriver(driver: SqlDriver): DATABASE

    /**
     * Schema for [DATABASE]
     */
    protected abstract val schema: SqlSchema<QueryResult.Value<Unit>>

    /**
     * Get [DATABASE] instance.
     * If database not initialized then initialize it with [lock] protection
     */
    protected suspend fun getDatabase(): DATABASE {
        return database ?: lock.withLock {
            database ?: initDatabase()
        }
    }

    /**
     * Init database and store it on local [database] property
     * Call this function allow only inside [lock] mutex!
     */
    private suspend fun initDatabase(): DATABASE {
        val driver = factory.create(name, schema)
        val db = createDatabaseFromDriver(driver)
        database = db
        return db
    }
}
