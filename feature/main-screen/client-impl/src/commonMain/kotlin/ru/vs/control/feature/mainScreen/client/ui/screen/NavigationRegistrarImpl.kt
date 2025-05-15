package ru.vs.control.feature.mainScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.TabNavigationHost
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams

internal class NavigationRegistrarImpl(
    private val mainScreenFactory: MainScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = mainScreenFactory,
            defaultParams = MainScreenParams,
            description = "Главный экран приложения. С табиками и тд.",
        ) {
            TabNavigationHost opens setOf(
                ServersScreenParams::class,
            )
        }
    }
}
