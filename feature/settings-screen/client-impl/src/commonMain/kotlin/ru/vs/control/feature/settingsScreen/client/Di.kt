package ru.vs.control.feature.settingsScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vladislavsumin.core.navigation.registration.bindNavigation
import ru.vs.control.feature.settingsScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen.SettingsViewModelFactory

fun Modules.featureSettingsScreen() = DI.Module("feature-settings-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = SettingsViewModelFactory()
        SettingsScreenFactory(viewModelFactory)
    }
}
