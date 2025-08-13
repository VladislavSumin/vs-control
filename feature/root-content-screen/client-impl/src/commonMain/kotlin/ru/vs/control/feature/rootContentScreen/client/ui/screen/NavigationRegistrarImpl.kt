package ru.vs.control.feature.rootContentScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenParams
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenFactory
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsNavigationRegistrar

internal class NavigationRegistrarImpl : VsNavigationRegistrar {
    override fun NavigationRegistry<VsComponentContext>.register() {
        registerScreen(
            factory = RootContentScreenFactory(),
            defaultParams = RootContentScreenParams,
            description = "Корневой экран приложения, на нем располагается главная стековая навигация.",
        ) {
            RootContentNavigationHost opens setOf(
                SettingsScreenParams::class,
                DebugScreenParams::class,
                AddEmbeddedServerScreenParams::class,
                AddServerScreenParams::class,
                AddServerByUrlScreenParams::class,
                MainScreenParams::class,
            )
        }
    }
}
