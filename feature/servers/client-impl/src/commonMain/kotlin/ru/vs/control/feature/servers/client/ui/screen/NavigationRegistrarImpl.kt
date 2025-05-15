package ru.vs.control.feature.servers.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams

internal class NavigationRegistrarImpl(
    private val serversScreenFactory: ServersScreenFactory,
    private val addServerScreenFactory: AddServerScreenFactory,
    private val addServerByUrlScreenFactory: AddServerByUrlScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        // TODO сделать отдельный хост для сервер табов со своим стеком.
        registerScreen(
            factory = serversScreenFactory,
            defaultParams = ServersScreenParams,
            description = "Список добавленных серверов",
        )

        registerScreen(
            factory = addServerScreenFactory,
            defaultParams = AddServerScreenParams,
            description = "Экран добавления нового подключения к серверу",
        )

        registerScreen(
            factory = addServerByUrlScreenFactory,
            defaultParams = AddServerByUrlScreenParams,
            description = "Экран добавления нового подключения к серверу по его domain name или ip",
        )
    }
}
