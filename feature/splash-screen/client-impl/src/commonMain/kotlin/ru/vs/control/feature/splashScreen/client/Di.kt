package ru.vs.control.feature.splashScreen.client

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.vladislavsumin.core.di.Modules
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactoryImpl
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenViewModelFactory

fun Modules.featureSplashScreen() = DI.Module("feature-splash-screen") {
    bindProvider<SplashScreenFactory> {
        val viewModel = SplashScreenViewModelFactory()
        SplashScreenFactoryImpl(viewModel)
    }
}
