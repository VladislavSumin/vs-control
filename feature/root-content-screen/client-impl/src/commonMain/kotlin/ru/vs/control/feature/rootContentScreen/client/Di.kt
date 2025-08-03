package ru.vs.control.feature.rootContentScreen.client

import org.kodein.di.DI
import ru.vladislavsumin.core.di.Modules
import ru.vs.control.feature.rootContentScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureRootContentScreen() = DI.Module("feature-root-content-screen") {
    bindNavigation { NavigationRegistrarImpl() }
}
