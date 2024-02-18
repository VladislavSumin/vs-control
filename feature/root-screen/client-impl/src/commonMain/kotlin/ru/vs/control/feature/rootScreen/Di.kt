package ru.vs.control.feature.rootScreen

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactory
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactoryImpl
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureRootScreen() = DI.Module("feature-root-screen") {
    bindSingleton<RootScreenFactory> { RootScreenFactoryImpl(i()) }

    bindProvider { RootScreenViewModelFactory(i()) }
}
