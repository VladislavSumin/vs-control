package ru.vs.control.feature.debugScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.debugScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureDebugScreen() = DI.Module("feature-debug-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = DebugViewModelFactory()
        DebugScreenFactory(viewModelFactory, i())
    }
}
