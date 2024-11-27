package ru.vs.control.feature.mainScreen.ui.screen

import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.TabNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

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
