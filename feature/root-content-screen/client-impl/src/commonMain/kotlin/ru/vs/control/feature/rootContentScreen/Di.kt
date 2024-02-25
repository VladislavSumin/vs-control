package ru.vs.control.feature.rootContentScreen

import org.kodein.di.DI
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.rootContentScreen.ui.screen.NavigationRegistrarImpl
import ru.vs.core.di.Modules
import ru.vs.core.navigation.registration.NavigationRegistrar

fun Modules.featureRootContentScreen() = DI.Module("feature-root-content-screen") {
    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl() } }
    }
}
