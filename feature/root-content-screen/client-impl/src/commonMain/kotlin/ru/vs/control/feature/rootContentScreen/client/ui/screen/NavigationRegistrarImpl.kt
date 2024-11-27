package ru.vs.control.feature.rootContentScreen.client.ui.screen

import ru.vs.control.feature.navigationRootScreen.client.ui.screen.RootNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenFactory
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = RootContentScreenFactory(),
            defaultParams = RootContentScreenParams,
            opensIn = setOf(RootNavigationHost),
            navigationHosts = setOf(RootContentNavigationHost),
            description = "Корневой экран приложения, на нем располагается главная стековая навигация.",
        )
    }
}
