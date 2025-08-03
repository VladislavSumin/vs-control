package ru.vs.control.feature.navigationRootScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen.RootNavigationScreenViewModelFactory
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureNavigationRootScreen() = DI.Module("feature-navigation-root-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = RootNavigationScreenViewModelFactory(i())
        RootNavigationScreenFactory(viewModelFactory)
    }
}
