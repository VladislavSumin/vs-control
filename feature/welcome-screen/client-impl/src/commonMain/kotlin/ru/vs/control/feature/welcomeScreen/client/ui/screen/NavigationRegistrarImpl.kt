package ru.vs.control.feature.welcomeScreen.client.ui.screen

import ru.vs.control.feature.navigationRootScreen.client.ui.screen.RootNavigationHost
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val welcomeScreenFactory: WelcomeScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = welcomeScreenFactory,
            defaultParams = WelcomeScreenParams,
            opensIn = setOf(RootNavigationHost),
            description = "FRW, отображается при первом запуске приложения",
        )
    }
}
