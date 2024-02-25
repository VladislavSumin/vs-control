package ru.vs.control.feature.navigationRootScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.core.navigation.NavigationRegistrar
import ru.vs.core.navigation.NavigationRegistry
import ru.vs.core.navigation.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(RootNavigationScreenParams::class), RootNavigationScreenFactory())
        registerDefaultScreenParams(RootNavigationScreenParams)
    }
}
