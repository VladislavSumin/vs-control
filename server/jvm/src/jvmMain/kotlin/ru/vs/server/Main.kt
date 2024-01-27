package ru.vs.server

import ru.vs.core.logger.api.LogLevel
import ru.vs.core.logger.api.logger
import ru.vs.core.logger.manager.ExternalLogger
import ru.vs.core.logger.manager.LoggerManager

fun main() {
    LoggerManager.init(
        object : ExternalLogger {
            override fun log(level: LogLevel, tag: String, msg: String) {
                println("[$level][$tag]: $msg")
            }

            override fun log(level: LogLevel, tag: String, throwable: Throwable, msg: String) {
                println("[$level][$tag]: $msg")
                throwable.printStackTrace()
            }
        }
    )
    val logger = logger("main")
    logger.i { "Server hello" }

    // Logger Factory тут недоступна.
}
