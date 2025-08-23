package ru.vs.control.feature.entitiesScreen.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.entitiesScreen.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen.EntitiesScreenFactory
import ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen.EntitiesViewModelFactory
import ru.vs.core.decompose.context.bindNavigation

fun Modules.featureEntitiesScreen() = DI.Module("feature-entities-screen") {
    bindNavigation { NavigationRegistrarImpl(i()) }

    bindSingleton {
        val viewModel = EntitiesViewModelFactory()
        EntitiesScreenFactory(viewModel)
    }
}
