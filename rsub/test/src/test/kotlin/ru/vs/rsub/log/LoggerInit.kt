package ru.vs.rsub.log

import ru.vladislavsumin.core.logger.manager.LoggerManager
import ru.vladislavsumin.core.logger.platform.initDefault

object LoggerInit {
    init {
        LoggerManager.initDefault()
    }
}
