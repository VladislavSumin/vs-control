package ru.vs.control

import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

/**
 * Вызывается на самом раннем этапе старта приложения.
 * В этом месте должен инициализироваться только самый базовый функционал.
 */
fun preInit() {
    LoggerManager.initDefault()
    InitLogger.i("preInit()")
}
