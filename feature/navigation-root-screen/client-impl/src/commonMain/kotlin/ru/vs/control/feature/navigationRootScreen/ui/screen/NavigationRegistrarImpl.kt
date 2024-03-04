package ru.vs.control.feature.navigationRootScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(RootNavigationScreenParams::class),
            factory = RootNavigationScreenFactory(),
            defaultParams = RootNavigationScreenParams,
            navigationHosts = listOf(RootNavigationHost),
        )
    }
}
