package ru.vs.rsub.log

import org.junit.platform.commons.logging.LoggerFactory
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

object LoggerInit {
    init {
        LoggerManager.initDefault()
    }
}