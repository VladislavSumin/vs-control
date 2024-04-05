package ru.vs.control.feature.welcomeScreen

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.welcomeScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar

fun Modules.featureWelcomeScreen() = DI.Module("feature-welcome-screen") {
    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl(i()) } }
    }

    bindProvider { WelcomeScreenFactory(i()) }
    bindProvider { WelcomeScreenViewModelFactory() }
}
