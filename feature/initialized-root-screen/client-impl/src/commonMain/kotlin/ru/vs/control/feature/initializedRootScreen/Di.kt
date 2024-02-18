package ru.vs.control.feature.initializedRootScreen

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen.InitializedRootScreenFactory
import ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen.InitializedRootScreenFactoryImpl
import ru.vs.core.di.Modules

fun Modules.featureInitializedRootScreen() = DI.Module("feature-initialized-root-screen") {
    bindProvider<InitializedRootScreenFactory> { InitializedRootScreenFactoryImpl() }
}
