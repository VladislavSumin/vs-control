package ru.vs.core.logger.default

import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.manager.ExternalLogger

internal actual fun createPlatformLogger(): ExternalLogger {
    return object : ExternalLogger {
        override fun log(level: LogLevel, tag: String, msg: String) {
            println("[$level][$tag]: $msg")
        }

        override fun log(level: LogLevel, tag: String, throwable: Throwable, msg: String) {
            println("[$level][$tag]: $msg")
            throwable.printStackTrace()
        }
    }
}
