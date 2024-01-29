package ru.vs.core.logger.platform

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.manager.ExternalLogger
import ru.vs.core.logger.manager.ExternalLoggerFactory

internal actual fun createPlatformLoggerFactory(): ExternalLoggerFactory {
    return ExternalLoggerFactory { tag ->
        val log4jLogger = LogManager.getLogger(tag)
        Log4jExternalLogger(log4jLogger)
    }
}

private class Log4jExternalLogger(private val logger: Logger) : ExternalLogger {
    override fun log(level: LogLevel, msg: String) {
        logger.log(level.toLevel(), msg)
    }

    override fun log(level: LogLevel, throwable: Throwable, msg: String) {
        logger.log(level.toLevel(), msg, throwable)
    }

    private fun LogLevel.toLevel(): Level {
        return when (this) {
            LogLevel.TRACE -> Level.TRACE
            LogLevel.DEBUG -> Level.DEBUG
            LogLevel.INFO -> Level.INFO
            LogLevel.WARN -> Level.WARN
            LogLevel.ERROR -> Level.ERROR
            LogLevel.FATAL -> Level.FATAL
            LogLevel.NONE -> Level.OFF
        }
    }
}
