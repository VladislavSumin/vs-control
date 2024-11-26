package ru.vs.control.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.serverInfo.featureServerInfo2
import ru.vs.control.server.domain.KeyStoreInteractor
import ru.vs.control.server.domain.KeyStoreInteractorImpl
import ru.vs.control.server.web.WebServer
import ru.vs.control.server.web.WebServerImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.ktor.server.coreKtorServer

internal fun createDi() = DI {
    importOnce(Modules.coreKtorServer())

    importOnce(Modules.featureServerInfo2())

    bindSingleton<KeyStoreInteractor> { KeyStoreInteractorImpl() }
    bindSingleton<WebServer> { WebServerImpl(i(), i()) }
}
