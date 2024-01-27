package ru.vs.core.logger.internal

import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.common.Logger

/**
 * Что бы одновременно и разделить api и manager на два разных модуля (нужно, что бы не позволить из api настраивать
 * логер) и сохранить общее мутабельное состояние приватным мы должны вынести его в отдельный модуль.
 */
var LoggerFactory: (tag: String, logLevel: LogLevel) -> Logger = { _, _ -> error("Logger not initialized") }
