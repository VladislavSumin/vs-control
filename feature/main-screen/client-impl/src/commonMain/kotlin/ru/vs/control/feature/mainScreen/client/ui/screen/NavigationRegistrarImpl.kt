package ru.vs.control.feature.mainScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.TabNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentNavigationHost

internal class NavigationRegistrarImpl(
    private val mainScreenFactory: MainScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = mainScreenFactory,
            defaultParams = MainScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            navigationHosts = setOf(TabNavigationHost),
            description = "Главный экран приложения. С табиками и тд.",
        )
    }
}
