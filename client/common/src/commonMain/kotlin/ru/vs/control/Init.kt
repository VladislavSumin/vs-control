package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindInstance
import org.kodein.di.direct
import ru.vs.control.feature.appInfo.featureAppInfo
import ru.vs.control.feature.initialization.domain.InitializedDependenciesBuilder
import ru.vs.control.feature.initialization.featureInitialization
import ru.vs.control.feature.initializedRootScreen.featureInitializedRootScreen
import ru.vs.control.feature.navigationRootScreen.featureNavigationRootScreen
import ru.vs.control.feature.rootScreen.featureRootScreen
import ru.vs.control.feature.splashScreen.featureSplashScreen
import ru.vs.core.di.Modules
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault
import ru.vs.navigation.coreNavigation

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

    val initializedDependenciesBuilder = InitializedDependenciesBuilder {
        importOnce(Modules.coreNavigation())
        importOnce(Modules.featureInitializedRootScreen())
        importOnce(Modules.featureNavigationRootScreen())
    }

    val preInitDi = DI {
        importOnce(Modules.featureAppInfo())
        importOnce(Modules.featureInitialization())
        importOnce(Modules.featureRootScreen())

        // Модуль Splash экрана добавляется в этот граф, так как Splash экран показывается еще до инициализации
        // основного графа.
        importOnce(Modules.featureSplashScreen())

        bindInstance { initializedDependenciesBuilder }
    }

    return preInitDi.direct
}
