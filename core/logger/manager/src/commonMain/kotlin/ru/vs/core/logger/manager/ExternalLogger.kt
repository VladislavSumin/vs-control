package ru.vs.core.logger.manager

import ru.vs.core.logger.api.LogLevel

/**
 * Внешний интерфейс для использования внешнего логера.
 */
interface ExternalLogger {
    fun log(level: LogLevel, tag: String, msg: String)
    fun log(level: LogLevel, tag: String, throwable: Throwable, msg: String)
}
