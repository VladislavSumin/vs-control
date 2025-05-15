package ru.vs.control.feature.welcomeScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenParams

internal class NavigationRegistrarImpl(
    private val welcomeScreenFactory: WelcomeScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = welcomeScreenFactory,
            defaultParams = WelcomeScreenParams,
            description = "FRW, отображается при первом запуске приложения",
        )
    }
}
