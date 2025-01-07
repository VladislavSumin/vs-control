package ru.vs.rsub.server.di

import org.kodein.di.DI
import org.kodein.di.bindSet
import ru.vs.core.di.Modules
import ru.vs.rsub.RSubServerInterface

fun Modules.rsubServer() = DI.Module("rsub-server") {
    // Декларируем множество для регистрации RSubServerInterface модулей.
    bindSet<RSubServerInterface>()
}
