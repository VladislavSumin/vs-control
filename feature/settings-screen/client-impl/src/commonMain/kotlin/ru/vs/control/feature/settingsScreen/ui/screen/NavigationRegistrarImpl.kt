package ru.vs.control.feature.settingsScreen.ui.screen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.control.feature.settingsScreen.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.ui.screen.settingsScreen.SettingsScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val settingsScreenFactory: SettingsScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.debugScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = SettingsScreenParams.asKey(),
            factory = settingsScreenFactory,
            defaultParams = SettingsScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Экран настроек",
        )
    }
}
