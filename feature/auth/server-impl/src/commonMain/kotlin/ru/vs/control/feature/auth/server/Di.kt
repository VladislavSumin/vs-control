package ru.vs.control.feature.auth.server

import org.kodein.di.DI
import ru.vladislavsumin.core.di.Modules
import ru.vs.control.feature.auth.server.module.ServerAuthModule
import ru.vs.core.ktor.server.bindKtorServerModule

fun Modules.featureAuth() = DI.Module("feature-auth") {
    bindKtorServerModule { ServerAuthModule() }
}
