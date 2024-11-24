package ru.vs.control.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.server.domain.KeyStoreInteractor
import ru.vs.control.server.domain.KeyStoreInteractorImpl
import ru.vs.control.server.web.WebServer
import ru.vs.control.server.web.WebServerImpl

internal fun createDi() = DI {
    bindSingleton<KeyStoreInteractor> { KeyStoreInteractorImpl() }
    bindSingleton<WebServer> { WebServerImpl() }
}
