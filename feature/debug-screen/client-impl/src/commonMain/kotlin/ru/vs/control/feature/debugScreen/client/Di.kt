package ru.vs.control.feature.debugScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vladislavsumin.core.navigation.registration.bindNavigation
import ru.vs.control.feature.debugScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugViewModelFactory

fun Modules.featureDebugScreen() = DI.Module("feature-debug-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = DebugViewModelFactory()
        DebugScreenFactory(viewModelFactory, i())
    }
}
