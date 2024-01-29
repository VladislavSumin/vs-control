package ru.vs.core.logger.manager

import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.internal.LoggerFactory

/**
 * Класс для настройки логера
 */
object LoggerManager {
    private var isInitialized = false

    /**
     * Инициализирует логер.
     * @param externalLoggerFactory реализация фабрики внешних логеров в который будет переданы все логи прошедшие
     * фильтрацию.
     */
    fun init(externalLoggerFactory: ExternalLoggerFactory, rootLogLevel: LogLevel = LogLevel.TRACE) {
        check(!isInitialized) { "Logger already initialized" }
        isInitialized = true
        LoggerFactory = { tag, logLevel ->
            LoggerImpl(
                logger = externalLoggerFactory.create(tag),
                logLevel = rootLogLevel merge logLevel,
            )
        }
    }
}
