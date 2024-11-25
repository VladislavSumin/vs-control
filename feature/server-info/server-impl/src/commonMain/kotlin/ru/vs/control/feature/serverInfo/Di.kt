package ru.vs.control.feature.serverInfo

import org.kodein.di.DI
import ru.vs.control.feature.serverInfo.module.ServerInfoModule
import ru.vs.core.di.Modules
import ru.vs.core.ktor.server.bindKtorServerModule

fun Modules.featureServerInfo() = DI.Module("feature-server-info") {
    bindKtorServerModule { ServerInfoModule() }
}
