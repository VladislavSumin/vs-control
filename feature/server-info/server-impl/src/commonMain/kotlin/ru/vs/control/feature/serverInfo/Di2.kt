package ru.vs.control.feature.serverInfo

import org.kodein.di.DI
import ru.vs.control.feature.serverInfo.module.ServerInfoModule
import ru.vs.core.di.Modules
import ru.vs.core.ktor.server.bindKtorServerModule

// TODO временно чисто.
fun Modules.featureServerInfo2() = DI.Module("feature-server-info") {
    bindKtorServerModule { ServerInfoModule() }
}
