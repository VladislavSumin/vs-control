package ru.vs.control.feature.welcomeScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val welcomeScreenFactory: WelcomeScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            key = WelcomeScreenParams.asKey(),
            factory = welcomeScreenFactory,
            defaultParams = WelcomeScreenParams,
            opensIn = setOf(RootNavigationHost),
            description = "FRW, отображается при первом запуске приложения",
        )
    }
}
