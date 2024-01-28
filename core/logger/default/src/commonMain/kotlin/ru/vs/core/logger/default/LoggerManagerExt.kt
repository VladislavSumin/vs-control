package ru.vs.core.logger.default

import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.manager.LoggerManager

/**
 * Инициализирует логер с помощью платформенного внешнего логера по умолчанию
 */
fun LoggerManager.initDefault(rootLogLevel: LogLevel = LogLevel.TRACE) {
    init(createPlatformLogger(), rootLogLevel)
}
