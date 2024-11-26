package ru.vs.control.feature.debugScreen.client.ui.screen

import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val debugScreenFactory: DebugScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = debugScreenFactory,
            defaultParams = DebugScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Debug экран, предоставляет доступ к элементам отладки приложения",
        )
    }
}
