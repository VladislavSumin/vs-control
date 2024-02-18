package ru.vs.control.feature.splashScreen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.splashScreen.ui.screen.splashScreen.SplashScreenFactoryImpl
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.di.Modules

fun Modules.featureSplashScreen() = DI.Module("feature-splash-screen") {
    bindSingleton<SplashScreenFactory> { SplashScreenFactoryImpl() }
}
