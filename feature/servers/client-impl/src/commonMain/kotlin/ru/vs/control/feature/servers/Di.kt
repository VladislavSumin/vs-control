package ru.vs.control.feature.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.servers.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlViewModelFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerViewModelFactory
import ru.vs.control.feature.servers.ui.screen.serversScreen.ServersScreenFactory
import ru.vs.control.feature.servers.ui.screen.serversScreen.ServersViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureServers() = DI.Module("feature-servers") {
    bindNavigation { NavigationRegistrarImpl(i(), i(), i()) }

    bindSingleton {
        val viewModelFactory = ServersViewModelFactory()
        ServersScreenFactory(viewModelFactory, i())
    }

    bindSingleton {
        val viewModelFactory = AddServerViewModelFactory(i())
        AddServerScreenFactory(viewModelFactory)
    }

    bindSingleton {
        val viewModelFactory = AddServerByUrlViewModelFactory()
        AddServerByUrlScreenFactory(viewModelFactory)
    }
}
