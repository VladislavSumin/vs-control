package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenComponent
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory

class SplashScreenFactoryImpl : SplashScreenFactory {
    override fun create(context: ComponentContext): SplashScreenComponent {
        return SplashScreenComponentImpl(context)
    }
}
