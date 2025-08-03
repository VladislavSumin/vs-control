package ru.vs.control.feature.initializedRootScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootScreenFactory
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootScreenFactoryImpl
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootViewModelFactory

fun Modules.featureInitializedRootScreen() = DI.Module("feature-initialized-root-screen") {
    bindSingleton<InitializedRootScreenFactory> {
        val viewModelFactory = InitializedRootViewModelFactory(i())
        InitializedRootScreenFactoryImpl(viewModelFactory)
    }
}
