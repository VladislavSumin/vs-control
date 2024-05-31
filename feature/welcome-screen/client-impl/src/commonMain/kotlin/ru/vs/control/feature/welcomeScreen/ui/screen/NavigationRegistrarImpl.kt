package ru.vs.control.feature.welcomeScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl(
    private val welcomeScreenFactory: WelcomeScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.welcomeScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(WelcomeScreenParams::class),
            factory = welcomeScreenFactory,
            paramsSerializer = WelcomeScreenParams.serializer(),
            nameForLogs = "WelcomeScreenParams",
            defaultParams = WelcomeScreenParams,
        )
        registerScreenNavigation(RootNavigationHost, ScreenKey(WelcomeScreenParams::class))
    }
}
