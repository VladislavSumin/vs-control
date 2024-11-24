package ru.vs.control.server

import kotlinx.coroutines.runBlocking
import ru.vs.core.logger.api.logger
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

fun main() {
    LoggerManager.initDefault()
    val logger = logger("main")
    logger.i { "Server hello" }

    runBlocking {
        Server().run()
    }
}
