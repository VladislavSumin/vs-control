package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import com.arkivanov.decompose.ComponentContext

internal class SplashScreenFactoryImpl(
    private val splashScreenViewModelFactory: SplashScreenViewModelFactory,
) : SplashScreenFactory {
    override fun create(context: ComponentContext): SplashScreenComponent {
        return SplashScreenComponent(
            splashScreenViewModelFactory,
            context,
        )
    }
}
