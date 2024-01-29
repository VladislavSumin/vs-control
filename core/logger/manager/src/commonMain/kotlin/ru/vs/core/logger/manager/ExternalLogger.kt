package ru.vs.core.logger.manager

import ru.vs.core.logger.common.LogLevel

/**
 * Внешний интерфейс для использования внешнего логера.
 */
interface ExternalLogger {
    fun log(level: LogLevel, msg: String)
    fun log(level: LogLevel, throwable: Throwable, msg: String)
}

fun interface ExternalLoggerFactory {
    fun create(tag: String): ExternalLogger
}
