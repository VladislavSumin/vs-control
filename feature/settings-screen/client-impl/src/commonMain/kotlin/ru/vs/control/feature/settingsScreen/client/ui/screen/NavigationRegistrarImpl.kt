package ru.vs.control.feature.settingsScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenParams

internal class NavigationRegistrarImpl(
    private val settingsScreenFactory: SettingsScreenFactory,
) : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreen(
            factory = settingsScreenFactory,
            defaultParams = SettingsScreenParams,
            description = "Экран настроек",
        )
    }
}
