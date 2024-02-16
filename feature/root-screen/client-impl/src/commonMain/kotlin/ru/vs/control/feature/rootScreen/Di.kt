package ru.vs.control.feature.rootScreen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactory
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactoryImpl
import ru.vs.core.di.Modules

fun Modules.featureRootScreen() = DI.Module("feature-root-screen") {
    bindSingleton<RootScreenFactory> { RootScreenFactoryImpl() }
}
