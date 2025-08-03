package ru.vs.control.feature.serverInfo.server

import org.kodein.di.DI
import ru.vladislavsumin.core.di.Modules
import ru.vs.control.feature.serverInfo.server.module.ServerInfoModule
import ru.vs.core.ktor.server.bindKtorServerModule

fun Modules.featureServerInfo() = DI.Module("feature-server-info") {
    bindKtorServerModule { ServerInfoModule() }
}
