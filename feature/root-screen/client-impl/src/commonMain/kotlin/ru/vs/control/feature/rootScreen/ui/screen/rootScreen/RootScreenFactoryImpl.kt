package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory

internal class RootScreenFactoryImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    private val splashScreenFactory: SplashScreenFactory,
) : RootScreenFactory {
    override fun create(context: ComponentContext): RootScreenComponent {
        return RootScreenComponentImpl(
            rootScreenViewModelFactory,
            splashScreenFactory,
            context,
        )
    }
}
