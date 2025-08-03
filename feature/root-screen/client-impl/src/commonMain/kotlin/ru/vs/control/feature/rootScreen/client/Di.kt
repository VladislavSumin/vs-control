package ru.vs.control.feature.rootScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenFactory
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenFactoryImpl
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenViewModelFactory

fun Modules.featureRootScreen() = DI.Module("feature-root-screen") {
    bindSingleton<RootScreenFactory> {
        val viewModelFactory = RootScreenViewModelFactory(i())
        RootScreenFactoryImpl(viewModelFactory, i())
    }
}
