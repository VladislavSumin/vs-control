package ru.vs.control.feature.embeddedServer.ui.screen

import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenParams
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val addEmbeddedServerScreenFactory: AddEmbeddedServerScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = addEmbeddedServerScreenFactory,
            defaultParams = AddEmbeddedServerScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления встроенного сервера.",
        )
    }
}
