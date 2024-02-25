package ru.vs.control.feature.debugScreen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.debugScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar

fun Modules.featureDebugScreen() = DI.Module("feature-debug-screen") {
    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl(i()) } }
    }

    bindSingleton { DebugScreenFactory(i()) }
}
