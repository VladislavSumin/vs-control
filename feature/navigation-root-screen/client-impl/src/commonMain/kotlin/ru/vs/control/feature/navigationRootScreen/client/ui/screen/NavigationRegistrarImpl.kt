package ru.vs.control.feature.navigationRootScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenParams

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
