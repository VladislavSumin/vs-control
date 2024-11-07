package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindInstance
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.direct
import ru.vs.control.feature.appInfo.featureAppInfo
import ru.vs.control.feature.debugScreen.featureDebugScreen
import ru.vs.control.feature.embeddedServer.featureEmbeddedServer
import ru.vs.control.feature.embeddedServer.service.EmbeddedServerQueriesProvider
import ru.vs.control.feature.initialization.domain.InitializedDependenciesBuilder
import ru.vs.control.feature.initialization.featureInitialization
import ru.vs.control.feature.initializedRootScreen.featureInitializedRootScreen
import ru.vs.control.feature.mainScreen.featureMainScreen
import ru.vs.control.feature.navigationRootScreen.featureNavigationRootScreen
import ru.vs.control.feature.rootContentScreen.featureRootContentScreen
import ru.vs.control.feature.rootScreen.featureRootScreen
import ru.vs.control.feature.servers.featureServers
import ru.vs.control.feature.splashScreen.featureSplashScreen
import ru.vs.control.feature.welcomeScreen.featureWelcomeScreen
import ru.vs.control.service.DatabaseService
import ru.vs.core.database.coreDatabase
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault
import ru.vs.core.navigation.coreNavigation

/**
 * Вызывается на самом раннем этапе старта приложения.
 * В этом месте должен инициализироваться только самый базовый функционал.
 *
 * @param preInitPlatformModule опциональный модуль di для передачи платформенных зависимостей.
 *
 * @return [DI] не инициализированного приложения. Этот DI содержит только самые базовые элементы необходимые для
 * дальнейшей инициализации проекта.
 */
fun preInit(preInitPlatformModule: DI.Module? = null): DirectDI {
    LoggerManager.initDefault()
    InitLogger.i("preInit()")

    // Граф инициализируется в фоне после базовой инициализации ui (во время показа splash экрана).
    val initializedDependenciesBuilder = InitializedDependenciesBuilder {
        importOnce(Modules.coreNavigation())
        importOnce(Modules.coreDatabase())

        importOnce(Modules.featureDebugScreen())
        importOnce(Modules.featureEmbeddedServer())
        importOnce(Modules.featureInitializedRootScreen())
        importOnce(Modules.featureMainScreen())
        importOnce(Modules.featureNavigationRootScreen())
        importOnce(Modules.featureRootContentScreen())
        importOnce(Modules.featureServers())
        importOnce(Modules.featureWelcomeScreen())

        bindSingleton { DatabaseService(i()) }
        bindProvider<EmbeddedServerQueriesProvider> { i<DatabaseService>() }
    }

    // Граф инициализируется до создания ui блокируя главный поток.
    val preInitDi = DI {
        if (preInitPlatformModule != null) {
            importOnce(preInitPlatformModule)
        }
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
