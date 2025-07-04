package ru.vs.control.feature.debugScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams

internal class NavigationRegistrarImpl(
    private val debugScreenFactory: DebugScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = debugScreenFactory,
            defaultParams = DebugScreenParams,
            description = "Debug экран, предоставляет доступ к элементам отладки приложения",
        )
    }
}
