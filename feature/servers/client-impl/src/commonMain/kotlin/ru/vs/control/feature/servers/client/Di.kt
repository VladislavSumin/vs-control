package ru.vs.control.feature.servers.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.servers.client.domain.ServerInteractorImplFactory
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.control.feature.servers.client.domain.ServersInteractorImpl
import ru.vs.control.feature.servers.client.repository.ServersRepositoryImpl
import ru.vs.control.feature.servers.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModelFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerViewModelFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersViewModelFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent.ServerComponentFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent.ServerViewModelFactory
import ru.vs.core.decompose.context.bindNavigation

fun Modules.featureServers() = DI.Module("feature-servers") {
    bindNavigation { NavigationRegistrarImpl(i(), i(), i()) }

    bindSingleton<ServersInteractor> {
        val repository = ServersRepositoryImpl(i(), i())
        val factory = ServerInteractorImplFactory(i(), i(), i())
        ServersInteractorImpl(repository, factory, i())
    }

    bindSingleton {
        val viewModelFactory = ServersViewModelFactory(i())
        ServersScreenFactory(viewModelFactory, i(), i())
    }

    bindSingleton {
        val viewModelFactory = ServerViewModelFactory(i(), i())
        ServerComponentFactory(viewModelFactory)
    }

    bindSingleton {
        val viewModelFactory = AddServerViewModelFactory(i())
        AddServerScreenFactory(viewModelFactory)
    }

    bindSingleton {
        val viewModelFactory = AddServerByUrlViewModelFactory(i(), i(), i())
        AddServerByUrlScreenFactory(viewModelFactory)
    }
}
