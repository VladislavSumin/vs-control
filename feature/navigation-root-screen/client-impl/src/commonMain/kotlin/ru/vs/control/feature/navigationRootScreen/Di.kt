package ru.vs.control.feature.navigationRootScreen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.navigationRootScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenFactory
import ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen.RootNavigationScreenViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureNavigationRootScreen() = DI.Module("feature-navigation-root-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = RootNavigationScreenViewModelFactory(i())
        RootNavigationScreenFactory(viewModelFactory)
    }
}
