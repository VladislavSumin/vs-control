package ru.vs.core.autoload

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i

fun Modules.coreAutoload() = DI.Module("core-autoload") {
    // Декларируем множество для регистрации AutoLoadService модулей.
    bindSet<AutoloadService>()

    bindSingleton<AutoloadInteractor> { AutoLoadInteractorImpl(i()) }
}
