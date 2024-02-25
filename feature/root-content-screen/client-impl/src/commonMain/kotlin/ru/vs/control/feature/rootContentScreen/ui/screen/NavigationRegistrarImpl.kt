package ru.vs.control.feature.rootContentScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenFactory
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(RootContentScreenParams::class), RootContentScreenFactory())
        registerDefaultScreenParams(RootContentScreenParams)
        registerScreenNavigation(RootNavigationHost, ScreenKey(RootContentScreenParams::class))
    }
}
