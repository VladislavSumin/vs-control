package ru.vs.control.feature.embeddedServer.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsNavigationRegistrar

internal class NavigationRegistrarImpl(
    private val addEmbeddedServerScreenFactory: AddEmbeddedServerScreenFactory,
) : VsNavigationRegistrar {
    override fun NavigationRegistry<VsComponentContext>.register() {
        registerScreen(
            factory = addEmbeddedServerScreenFactory,
            defaultParams = AddEmbeddedServerScreenParams,
            description = "Экран добавления встроенного сервера.",
        )
    }
}
