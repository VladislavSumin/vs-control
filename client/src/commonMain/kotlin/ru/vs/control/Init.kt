package ru.vs.control

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindInstance
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.direct
import ru.vladislavsumin.core.logger.manager.LoggerManager
import ru.vladislavsumin.core.logger.platform.initDefault
import ru.vs.control.feature.appInfo.client.featureAppInfo
import ru.vs.control.feature.auth.client.featureAuth
import ru.vs.control.feature.debugScreen.client.featureDebugScreen
import ru.vs.control.feature.embeddedServer.client.featureEmbeddedServer
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServerQueriesProvider
import ru.vs.control.feature.entities.client.featureEntities
import ru.vs.control.feature.initialization.client.domain.InitializedDependenciesBuilder
import ru.vs.control.feature.initialization.client.featureInitialization
import ru.vs.control.feature.initializedRootScreen.client.featureInitializedRootScreen
import ru.vs.control.feature.mainScreen.client.featureMainScreen
import ru.vs.control.feature.navigationRootScreen.client.featureNavigationRootScreen
import ru.vs.control.feature.rootContentScreen.client.featureRootContentScreen
import ru.vs.control.feature.rootScreen.client.featureRootScreen
import ru.vs.control.feature.serverInfo.client.featureServerInfo
import ru.vs.control.feature.servers.client.featureServers
import ru.vs.control.feature.servers.client.repository.ServerQueriesProvider
import ru.vs.control.feature.settingsScreen.client.featureSettingsScreen
import ru.vs.control.feature.splashScreen.client.featureSplashScreen
import ru.vs.control.feature.welcomeScreen.client.featureWelcomeScreen
import ru.vs.control.service.DatabaseService
import ru.vs.core.autoload.coreAutoload
import ru.vs.core.coroutines.DispatchersProvider
import ru.vs.core.coroutines.coreCoroutines
import ru.vs.core.database.coreDatabase
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.fs.coreFs
import ru.vs.core.ktor.client.coreKtorClient
import ru.vs.core.navigation.coreNavigation
import ru.vs.core.properties.coreProperties
import ru.vs.core.serialization.protobuf.coreSerializationProtobuf

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
        importOnce(Modules.coreAutoload())
        importOnce(Modules.coreNavigation())
        importOnce(Modules.coreDatabase())
        importOnce(Modules.coreKtorClient())
        importOnce(Modules.coreSerializationProtobuf())

        importOnce(Modules.featureAuth())
        importOnce(Modules.featureDebugScreen())
        importOnce(Modules.featureEmbeddedServer())
        importOnce(Modules.featureEntities())
        importOnce(Modules.featureInitializedRootScreen())
        importOnce(Modules.featureMainScreen())
        importOnce(Modules.featureNavigationRootScreen())
        importOnce(Modules.featureRootContentScreen())
        importOnce(Modules.featureServerInfo())
        importOnce(Modules.featureServers())
        importOnce(Modules.featureSettingsScreen())
        importOnce(Modules.featureWelcomeScreen())

        bindSingleton { DatabaseService(i()) }
        bindProvider<EmbeddedServerQueriesProvider> { i<DatabaseService>() }
        bindProvider<ServerQueriesProvider> { i<DatabaseService>() }
    }

    // Граф инициализируется до создания ui блокируя главный поток.
    val preInitDi = DI {
        if (preInitPlatformModule != null) {
            importOnce(preInitPlatformModule)
        }

        importOnce(Modules.coreCoroutines())
        importOnce(Modules.coreFs())
        importOnce(Modules.coreProperties())

        importOnce(Modules.featureAppInfo())
        importOnce(Modules.featureInitialization())
        importOnce(Modules.featureRootScreen())

        // Глобальный AppScope
        bindSingleton<CoroutineScope> {
            val dispatchersProvider = i<DispatchersProvider>()
            CoroutineScope(dispatchersProvider.default)
        }

        // Модуль Splash экрана добавляется в этот граф, так как Splash экран показывается еще до инициализации
        // основного графа.
        importOnce(Modules.featureSplashScreen())

        bindInstance { initializedDependenciesBuilder }
    }

    return preInitDi.direct
}
