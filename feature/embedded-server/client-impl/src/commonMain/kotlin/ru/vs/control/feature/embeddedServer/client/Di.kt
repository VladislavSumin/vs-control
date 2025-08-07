package ru.vs.control.feature.embeddedServer.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vladislavsumin.core.navigation.registration.bindNavigation
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServerInteractorFactory
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServerSupportInteractor
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServerSupportInteractorImpl
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServersInteractorImpl
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServersRepositoryImpl
import ru.vs.control.feature.embeddedServer.client.service.EmbeddedServerLoadService
import ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactory
import ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactoryImpl
import ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent.EmbeddedServersListViewModelFactory
import ru.vs.control.feature.embeddedServer.client.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerViewModelFactory
import ru.vs.core.autoload.bindAutoload

fun Modules.featureEmbeddedServer() = DI.Module("feature-embedded-server") {
    importOnce(featureEmbeddedServerPlatform())

    bindNavigation { NavigationRegistrarImpl(i()) }
    bindAutoload { EmbeddedServerLoadService(i()) }

    bindSingleton<EmbeddedServersInteractor> {
        EmbeddedServersInteractorImpl(
            embeddedServerInteractorFactory = EmbeddedServerInteractorFactory(),
            embeddedServersRepository = EmbeddedServersRepositoryImpl(i(), i()),
            scope = i(),
        )
    }
    bindSingleton<EmbeddedServerSupportInteractor> { EmbeddedServerSupportInteractorImpl() }

    bindSingleton<EmbeddedServersListComponentFactory> {
        val viewModelFactory = EmbeddedServersListViewModelFactory(i())
        EmbeddedServersListComponentFactoryImpl(viewModelFactory)
    }

    bindSingleton {
        val viewModelFactory = AddEmbeddedServerViewModelFactory(i())
        AddEmbeddedServerScreenFactory(viewModelFactory)
    }
}

internal expect fun Modules.featureEmbeddedServerPlatform(): DI.Module
