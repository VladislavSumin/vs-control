package ru.vs.control.feature.serverInfo.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.serverInfo.rSub.ServerInfoRsubRSubServerProxy
import ru.vs.control.feature.serverInfo.server.domain.ServerInfoInteractor
import ru.vs.control.feature.serverInfo.server.domain.ServerInfoInteractorImpl
import ru.vs.control.feature.serverInfo.server.module.ServerInfoModule
import ru.vs.control.feature.serverInfo.server.rsub.ServerInfoRsubImpl
import ru.vs.core.ktor.server.bindKtorServerModule
import ru.vs.rsub.server.di.bindRsubServerInterface

fun Modules.featureServerInfo() = DI.Module("feature-server-info") {
    bindSingleton<ServerInfoInteractor> { ServerInfoInteractorImpl() }
    bindKtorServerModule { ServerInfoModule(i()) }
    bindRsubServerInterface {
        val rsub = ServerInfoRsubImpl(i())
        ServerInfoRsubRSubServerProxy(rsub)
    }
}
