package ru.vs.control.feature.navigationRootScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.navigation.NavigationRegistrar
import ru.vs.navigation.NavigationRegistry

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(RootNavigationScreenParams::class, RootNavigationScreenFactory())
        registerDefaultScreenParams(RootNavigationScreenParams)
    }
}
