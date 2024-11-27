package ru.vs.control.feature.splashScreen.client

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactoryImpl
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenViewModelFactory
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.di.Modules

fun Modules.featureSplashScreen() = DI.Module("feature-splash-screen") {
    bindProvider<SplashScreenFactory> {
        val viewModel = SplashScreenViewModelFactory()
        SplashScreenFactoryImpl(viewModel)
    }
}
