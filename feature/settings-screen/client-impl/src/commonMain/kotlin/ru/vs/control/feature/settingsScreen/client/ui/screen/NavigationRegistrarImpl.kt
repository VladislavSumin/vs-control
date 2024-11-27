package ru.vs.control.feature.settingsScreen.client.ui.screen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry

internal class NavigationRegistrarImpl(
    private val settingsScreenFactory: SettingsScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = settingsScreenFactory,
            defaultParams = SettingsScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран настроек",
        )
    }
}
