package ru.vs.control.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.auth.server.featureAuth
import ru.vs.control.feature.entities.server.featureEntities
import ru.vs.control.feature.rsub.server.featureRsub
import ru.vs.control.feature.serverInfo.server.featureServerInfo
import ru.vs.control.server.web.WebServer
import ru.vs.control.server.web.WebServerImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.ktor.server.coreKtorServer
import ru.vs.core.serialization.protobuf.coreSerializationProtobuf

internal fun createDi() = DI {
    importOnce(Modules.coreSerializationProtobuf())
    importOnce(Modules.coreKtorServer())

    importOnce(Modules.featureAuth())
    importOnce(Modules.featureEntities())
    importOnce(Modules.featureRsub())
    importOnce(Modules.featureServerInfo())

    // bindSingleton<KeyStoreInteractor> { KeyStoreInteractorImpl() }
    bindSingleton<WebServer> { WebServerImpl(i()) }
}
