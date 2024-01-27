package ru.vs.core.logger.manager

import ru.vs.core.logger.api.LogLevel
import ru.vs.core.logger.internal.LoggerFactory

/**
 * Класс для настройки логера
 */
object LoggerManager {
    private var isInitialized = false

    /**
     * Инициализирует логер.
     * @param externalLogger реализация внешнего логера в который будет переданы все логи прошедшие фильтрацию.
     */
    fun init(externalLogger: ExternalLogger, rootLogLevel: LogLevel = LogLevel.TRACE) {
        check(!isInitialized) { "Logger already initialized" }
        isInitialized = true
        LoggerFactory = { tag, logLevel ->
            LoggerImpl(
                logger = externalLogger,
                logLevel = rootLogLevel merge (logLevel as LogLevel),
                tag = tag,
            )
        }
    }
}