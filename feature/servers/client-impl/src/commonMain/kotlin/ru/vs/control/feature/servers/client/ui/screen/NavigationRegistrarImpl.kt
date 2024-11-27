package ru.vs.control.feature.servers.client.ui.screen

import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.TabNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val serversScreenFactory: ServersScreenFactory,
    private val addServerScreenFactory: AddServerScreenFactory,
    private val addServerByUrlScreenFactory: AddServerByUrlScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = serversScreenFactory,
            defaultParams = ServersScreenParams,
            opensIn = setOf(TabNavigationHost), // TODO сделать отдельный хост для сервер табов со своим стеком.
            description = "Список добавленных серверов",
        )

        registerScreen(
            factory = addServerScreenFactory,
            defaultParams = AddServerScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления нового подключения к серверу",
        )

        registerScreen(
            factory = addServerByUrlScreenFactory,
            defaultParams = AddServerByUrlScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления нового подключения к серверу по его domain name или ip",
        )
    }
}
