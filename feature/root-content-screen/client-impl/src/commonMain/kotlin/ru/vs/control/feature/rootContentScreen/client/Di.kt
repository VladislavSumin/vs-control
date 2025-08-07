package ru.vs.control.feature.rootContentScreen.client

import org.kodein.di.DI
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.navigation.registration.bindNavigation
import ru.vs.control.feature.rootContentScreen.client.ui.screen.NavigationRegistrarImpl

fun Modules.featureRootContentScreen() = DI.Module("feature-root-content-screen") {
    bindNavigation { NavigationRegistrarImpl() }
}
