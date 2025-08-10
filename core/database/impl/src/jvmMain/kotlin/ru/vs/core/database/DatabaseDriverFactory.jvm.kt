package ru.vs.core.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.kodein.di.DirectDI
import ru.vladislavsumin.core.di.i
import ru.vs.core.fs.service.FileSystemService
import java.io.File

private class JvmDatabaseDriverFactory(
    private val fileSystemService: FileSystemService,
) : DatabaseDriverFactory {
    override suspend fun create(name: String, schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        // TODO add normal schema check
        val pathStr = "${fileSystemService.getDatabaseDirPath()}/$name.db"
        val isDbExists = File(pathStr).exists()
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$pathStr")
        if (!isDbExists) {
            schema.create(driver)
        }
        return driver
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return JvmDatabaseDriverFactory(i())
}
