package ru.vs.control.feature.debugScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenFactory
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsNavigationRegistrar

internal class NavigationRegistrarImpl(
    private val debugScreenFactory: DebugScreenFactory,
) : VsNavigationRegistrar {
    override fun NavigationRegistry<VsComponentContext>.register() {
        registerScreen(
            factory = debugScreenFactory,
            defaultParams = DebugScreenParams,
            description = "Debug экран, предоставляет доступ к элементам отладки приложения",
        )
    }
}
