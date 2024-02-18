package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct
import ru.vs.control.feature.appInfo.featureAppInfo
import ru.vs.control.feature.initialization.featureInitialization
import ru.vs.control.feature.rootScreen.featureRootScreen
import ru.vs.core.di.Modules
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

/**
 * Вызывается на самом раннем этапе старта приложения.
 * В этом месте должен инициализироваться только самый базовый функционал.
 *
 * @return [DI] не инициализированного приложения. Этот DI содержит только самые базовые элементы необходимые для
 * дальнейшей инициализации проекта.
 */
fun preInit(): DirectDI {
    LoggerManager.initDefault()
    InitLogger.i("preInit()")

    val preInitDi = DI {
        importOnce(Modules.featureAppInfo())
        importOnce(Modules.featureInitialization())
        importOnce(Modules.featureRootScreen())
    }

    return preInitDi.direct
}
