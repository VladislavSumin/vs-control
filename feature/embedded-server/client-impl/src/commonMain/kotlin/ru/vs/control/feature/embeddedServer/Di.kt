package ru.vs.control.feature.embeddedServer

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerInteractorFactory
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractor
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractorImpl
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractorImpl
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepository
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepositoryImpl
import ru.vs.control.feature.embeddedServer.service.EmbeddedServerLoadService
import ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactory
import ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactoryImpl
import ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent.EmbeddedServersListViewModelFactory
import ru.vs.control.feature.embeddedServer.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerViewModelFactory
import ru.vs.core.autoload.bindAutoload
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.bindNavigation

fun Modules.featureEmbeddedServer() = DI.Module("feature-embedded-server") {
    importOnce(featureEmbeddedServerPlatform())

    bindNavigation { NavigationRegistrarImpl(i()) }
    bindAutoload { EmbeddedServerLoadService(i()) }

    bindSingleton<EmbeddedServersInteractor> {
        EmbeddedServersInteractorImpl(
            embeddedServerInteractorFactory = EmbeddedServerInteractorFactory(),
            embeddedServersRepository = EmbeddedServersRepositoryImpl(i()),
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
