package ru.vs.core.ktor.server

import org.kodein.di.DI
import org.kodein.di.bindSet
import ru.vs.core.di.Modules

fun Modules.coreKtorServer() = DI.Module("core-ktor-server") {
    bindSet<KtorServerModule>()
}
