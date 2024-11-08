package ru.vs.control.feature.servers.ui.screen

import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.TabNavigationHost
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlScreenParams
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.control.feature.servers.ui.screen.serversScreen.ServersScreenFactory
import ru.vs.control.feature.servers.ui.screen.serversScreen.ServersScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val serversScreenFactory: ServersScreenFactory,
    private val addServerScreenFactory: AddServerScreenFactory,
    private val addServerByUrlScreenFactory: AddServerByUrlScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.servers.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ServersScreenParams.asKey(),
            factory = serversScreenFactory,
            nameForLogs = "ServersScreenParams",
            defaultParams = ServersScreenParams,
            opensIn = setOf(TabNavigationHost), // TODO сделать отдельный хост для сервер табов со своим стеком.
            description = "Список добавленных серверов",
        )

        registerScreen(
            key = AddServerScreenParams.asKey(),
            factory = addServerScreenFactory,
            nameForLogs = "AddServerScreenParams",
            defaultParams = AddServerScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления нового подключения к серверу",
        )

        registerScreen(
            key = AddServerByUrlScreenParams.asKey(),
            factory = addServerByUrlScreenFactory,
            nameForLogs = "AddServerByUrlScreenParams",
            defaultParams = AddServerByUrlScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран добавления нового подключения к серверу по его domain name или ip",
        )
    }
}
