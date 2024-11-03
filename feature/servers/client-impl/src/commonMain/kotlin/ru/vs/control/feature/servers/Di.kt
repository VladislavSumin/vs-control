package ru.vs.control.feature.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.servers.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar

fun Modules.featureServers() = DI.Module("feature-servers") {
    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl(i()) } }
    }

    bindSingleton {
        val viewModelFactory = AddServerViewModelFactory()
        AddServerScreenFactory(viewModelFactory)
    }
}
