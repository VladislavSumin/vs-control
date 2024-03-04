package ru.vs.control.feature.welcomeScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(WelcomeScreenParams::class),
            factory = WelcomeScreenFactory(),
            defaultParams = WelcomeScreenParams,
        )
        registerScreenNavigation(RootNavigationHost, ScreenKey(WelcomeScreenParams::class))
    }
}
