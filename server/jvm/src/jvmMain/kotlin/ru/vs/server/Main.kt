package ru.vs.server

import ru.vs.core.logger.api.logger
import ru.vs.core.logger.default.initDefault
import ru.vs.core.logger.manager.LoggerManager

fun main() {
    LoggerManager.initDefault()
    val logger = logger("main")
    logger.i { "Server hello" }
}
