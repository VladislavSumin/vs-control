package ru.vs.control.feature.initializedRootScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootScreenFactory
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootScreenFactoryImpl
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureInitializedRootScreen() = DI.Module("feature-initialized-root-screen") {
    bindSingleton<InitializedRootScreenFactory> {
        val viewModelFactory = InitializedRootViewModelFactory(i())
        InitializedRootScreenFactoryImpl(viewModelFactory)
    }
}
