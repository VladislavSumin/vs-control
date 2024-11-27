package ru.vs.control.feature.navigationRootScreen.client.ui.screen

import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val rootNavigationScreenFactory: RootNavigationScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = rootNavigationScreenFactory,
            defaultParams = RootNavigationScreenParams,
            navigationHosts = setOf(RootNavigationHost),
            description = "Корень графа навигации, переключает навигацию между FRW и главным экраном",
        )
    }
}
