package ru.vs.control.feature.navigationRootScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(RootNavigationScreenParams::class), RootNavigationScreenFactory())
        registerDefaultScreenParams(RootNavigationScreenParams)
        registerNavigationHost(ScreenKey(RootNavigationScreenParams::class), RootNavigationHost)
    }
}
