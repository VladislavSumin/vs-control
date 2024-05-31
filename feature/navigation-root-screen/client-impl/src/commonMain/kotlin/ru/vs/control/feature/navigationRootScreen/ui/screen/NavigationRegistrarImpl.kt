package ru.vs.control.feature.navigationRootScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl(
    private val rootNavigationScreenFactory: RootNavigationScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.navigationRootScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(RootNavigationScreenParams::class),
            factory = rootNavigationScreenFactory,
            paramsSerializer = RootNavigationScreenParams.serializer(),
            defaultParams = RootNavigationScreenParams,
            navigationHosts = listOf(RootNavigationHost),
        )
    }
}
