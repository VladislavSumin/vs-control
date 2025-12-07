package ru.vs.control.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vladislavsumin.core.serialization.protobuf.coreSerializationProtobuf
import ru.vs.control.feature.auth.server.featureAuth
import ru.vs.control.feature.entities.server.featureEntities
import ru.vs.control.feature.rsub.server.featureRsub
import ru.vs.control.feature.serverInfo.server.featureServerInfo
import ru.vs.control.server.web.WebServer
import ru.vs.control.server.web.WebServerImpl
import ru.vs.core.ktor.server.coreKtorServer

internal fun createDi() = DI {
    importOnce(Modules.coreSerializationProtobuf())
    importOnce(Modules.coreKtorServer())

    // Декларирует зависимости для rSub поэтому должен быть выше других фичей
    importOnce(Modules.featureRsub())

    importOnce(Modules.featureAuth())
    importOnce(Modules.featureEntities())
    importOnce(Modules.featureServerInfo())

    // bindSingleton<KeyStoreInteractor> { KeyStoreInteractorImpl() }
    bindSingleton<WebServer> { WebServerImpl(i()) }
}
