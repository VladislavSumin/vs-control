package ru.vs.core.logger.api

import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.common.Logger
import ru.vs.core.logger.internal.LoggerFactory

/**
 * Возвращает логер с базовым тегом [tag] и собственным уровнем логирования [logLevel].
 * Собственный уровень логирования фильтрует логи только в рамках этого логера. Так же уровень логов дополнительно
 * может быть понижен (понижен, то есть сужен) уровнем логирования установленного при инициализации менеджера.
 */
fun logger(tag: String, logLevel: LogLevel = LogLevel.TRACE): Logger = LoggerFactory(tag, logLevel)
