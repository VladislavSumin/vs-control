package ru.vs.control.feature.rsub.server

import org.kodein.di.DI
import ru.vs.control.feature.rsub.server.module.RSubModule
import ru.vs.core.di.Modules
import ru.vs.core.ktor.server.bindKtorServerModule

fun Modules.featureRsub() = DI.Module("feature-rsub") {
    bindKtorServerModule { RSubModule() }
}
