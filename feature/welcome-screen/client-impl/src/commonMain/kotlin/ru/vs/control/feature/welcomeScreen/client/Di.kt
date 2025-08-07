package ru.vs.control.feature.welcomeScreen.client

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vladislavsumin.core.navigation.registration.bindNavigation
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractor
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorImpl
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorInternal
import ru.vs.control.feature.welcomeScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenViewModelFactory

fun Modules.featureWelcomeScreen() = DI.Module("feature-welcome-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton<WelcomeInteractorInternal> { WelcomeInteractorImpl(i()) }
    bindProvider<WelcomeInteractor> { i<WelcomeInteractorInternal>() }

    bindProvider {
        val viewModelFactory = WelcomeScreenViewModelFactory(i())
        WelcomeScreenFactory(viewModelFactory)
    }
}
