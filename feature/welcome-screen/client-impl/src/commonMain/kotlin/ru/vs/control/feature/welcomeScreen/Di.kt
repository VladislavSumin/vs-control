package ru.vs.control.feature.welcomeScreen

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.control.feature.welcomeScreen.domain.WelcomeInteractor
import ru.vs.control.feature.welcomeScreen.domain.WelcomeInteractorImpl
import ru.vs.control.feature.welcomeScreen.domain.WelcomeInteractorInternal
import ru.vs.control.feature.welcomeScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureWelcomeScreen() = DI.Module("feature-welcome-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton<WelcomeInteractorInternal> { WelcomeInteractorImpl() }
    bindProvider<WelcomeInteractor> { i<WelcomeInteractorInternal>() }

    bindProvider {
        val viewModelFactory = WelcomeScreenViewModelFactory()
        WelcomeScreenFactory(viewModelFactory)
    }
}
