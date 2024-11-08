package ru.vs.control.feature.settingsScreen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.settingsScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.settingsScreen.ui.screen.settingsScreen.SettingsScreenFactory
import ru.vs.control.feature.settingsScreen.ui.screen.settingsScreen.SettingsViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureSettingsScreen() = DI.Module("feature-settings-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = SettingsViewModelFactory()
        SettingsScreenFactory(viewModelFactory)
    }
}
