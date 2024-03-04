package ru.vs.control.feature.debugScreen.ui.screen

import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl(
    private val debugScreenFactory: DebugScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(DebugScreenParams::class),
            factory = debugScreenFactory,
            defaultParams = DebugScreenParams,
        )
        registerScreenNavigation(RootNavigationHost, ScreenKey(DebugScreenParams::class))
    }
}
