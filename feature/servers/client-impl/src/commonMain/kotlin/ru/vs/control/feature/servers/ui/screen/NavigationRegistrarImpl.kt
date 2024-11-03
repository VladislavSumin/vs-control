package ru.vs.control.feature.servers.ui.screen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val addServerScreenFactory: AddServerScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.servers.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = AddServerScreenParams.asKey(),
            factory = addServerScreenFactory,
            paramsSerializer = AddServerScreenParams.serializer(),
            nameForLogs = "AddServerScreenParams",
            defaultParams = AddServerScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления нового подключения к серверу",
        )
    }
}
