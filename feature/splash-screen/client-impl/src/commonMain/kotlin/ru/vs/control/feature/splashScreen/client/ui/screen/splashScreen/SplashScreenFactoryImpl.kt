package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import ru.vs.core.decompose.context.VsComponentContext

internal class SplashScreenFactoryImpl(
    private val splashScreenViewModelFactory: SplashScreenViewModelFactory,
) : SplashScreenFactory {
    override fun create(context: VsComponentContext): SplashScreenComponent {
        return SplashScreenComponent(
            splashScreenViewModelFactory,
            context,
        )
    }
}
