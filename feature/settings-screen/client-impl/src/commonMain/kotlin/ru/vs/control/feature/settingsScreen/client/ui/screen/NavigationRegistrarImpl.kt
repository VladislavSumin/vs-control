package ru.vs.control.feature.settingsScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsNavigationRegistrar

internal class NavigationRegistrarImpl(
    private val settingsScreenFactory: SettingsScreenFactory,
) : VsNavigationRegistrar {
    override fun NavigationRegistry<VsComponentContext>.register() {
        registerScreen(
            factory = settingsScreenFactory,
            defaultParams = SettingsScreenParams,
            description = "Экран настроек",
        )
    }
}
