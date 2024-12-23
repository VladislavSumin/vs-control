package ru.vs.rsub.log

import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

object LoggerInit {
    init {
        LoggerManager.initDefault()
    }
}
