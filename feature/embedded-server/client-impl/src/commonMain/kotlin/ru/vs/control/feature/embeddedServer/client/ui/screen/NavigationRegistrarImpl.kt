package ru.vs.control.feature.embeddedServer.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenParams

internal class NavigationRegistrarImpl(
    private val addEmbeddedServerScreenFactory: AddEmbeddedServerScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = addEmbeddedServerScreenFactory,
            defaultParams = AddEmbeddedServerScreenParams,
            description = "Экран добавления встроенного сервера.",
        )
    }
}
