package ru.vs.control.feature.debugScreen.ui.screen

import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val debugScreenFactory: DebugScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.debugScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = DebugScreenParams.asKey(),
            factory = debugScreenFactory,
            defaultParams = DebugScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Debug экран, предоставляет доступ к элементам отладки приложения",
        )
    }
}
