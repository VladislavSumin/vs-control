package ru.vs.core.database

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules

fun Modules.coreDatabase() = DI.Module("core-database") {
    bindSingleton<DatabaseDriverFactory> { createDatabaseFactory() }
}
