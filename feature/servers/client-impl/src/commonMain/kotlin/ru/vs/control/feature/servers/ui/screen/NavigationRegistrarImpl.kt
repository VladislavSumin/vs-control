package ru.vs.control.feature.servers.ui.screen

import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl(
    private val addServerScreenFactory: AddServerScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.servers.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(AddServerScreenParams::class),
            factory = addServerScreenFactory,
            paramsSerializer = AddServerScreenParams.serializer(),
            nameForLogs = "AddServerScreenParams",
            defaultParams = AddServerScreenParams,
        )
        registerScreenNavigation(TodoNavigationHost, ScreenKey(AddServerScreenParams::class))
    }
}

// TODO сделать нормальную иерархию экранов.
private object TodoNavigationHost : NavigationHost