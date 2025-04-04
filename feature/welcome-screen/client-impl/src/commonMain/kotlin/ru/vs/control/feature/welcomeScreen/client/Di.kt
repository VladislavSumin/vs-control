package ru.vs.control.feature.welcomeScreen.client

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractor
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorImpl
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorInternal
import ru.vs.control.feature.welcomeScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureWelcomeScreen() = DI.Module("feature-welcome-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton<WelcomeInteractorInternal> { WelcomeInteractorImpl(i()) }
    bindProvider<WelcomeInteractor> { i<WelcomeInteractorInternal>() }

    bindProvider {
        val viewModelFactory = WelcomeScreenViewModelFactory(i())
        WelcomeScreenFactory(viewModelFactory)
    }
}
