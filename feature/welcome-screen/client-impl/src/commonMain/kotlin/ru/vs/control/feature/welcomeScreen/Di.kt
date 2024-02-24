package ru.vs.control.feature.welcomeScreen

import org.kodein.di.DI
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.welcomeScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.core.di.Modules
import ru.vs.navigation.NavigationRegistrar

fun Modules.featureWelcomeScreen() = DI.Module("feature-welcome-screen") {
    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl() } }
    }
}
