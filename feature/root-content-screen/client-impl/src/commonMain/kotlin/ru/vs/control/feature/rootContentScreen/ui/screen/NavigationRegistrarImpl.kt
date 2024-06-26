package ru.vs.control.feature.rootContentScreen.ui.screen

import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenFactory
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.rootContentScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(RootContentScreenParams::class),
            factory = RootContentScreenFactory(),
            paramsSerializer = RootContentScreenParams.serializer(),
            nameForLogs = "RootContentScreenParams",
            defaultParams = RootContentScreenParams,
        )
        registerScreenNavigation(RootNavigationHost, ScreenKey(RootContentScreenParams::class))
    }
}
