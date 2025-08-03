package ru.vs.control.feature.mainScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.mainScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainViewModelFactory
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureMainScreen() = DI.Module("feature-main-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModelFactory = MainViewModelFactory()
        MainScreenFactory(viewModelFactory)
    }
}
