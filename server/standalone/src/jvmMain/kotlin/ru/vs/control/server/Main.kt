package ru.vs.control.server

import kotlinx.coroutines.runBlocking
import ru.vladislavsumin.core.logger.api.logger
import ru.vladislavsumin.core.logger.manager.LoggerManager
import ru.vladislavsumin.core.logger.platform.initDefault

fun main() {
    LoggerManager.initDefault()
    val logger = logger("main")
    logger.i { "Server hello" }

    runBlocking {
        Server().run()
    }
}
