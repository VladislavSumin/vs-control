package ru.vs.control.feature.embeddedServer

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractor
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractorImpl
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractorImpl
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepository
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServersRepositoryImpl
import ru.vs.control.feature.embeddedServer.ui.screen.NavigationRegistrarImpl
import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenFactory
import ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar

fun Modules.featureEmbeddedServer() = DI.Module("feature-embedded-server") {
    importOnce(featureEmbeddedServerPlatform())

    inBindSet<NavigationRegistrar> {
        add { singleton { NavigationRegistrarImpl(i()) } }
    }

    bindSingleton<EmbeddedServersRepository> { EmbeddedServersRepositoryImpl(i()) }
    bindSingleton<EmbeddedServersInteractor> { EmbeddedServersInteractorImpl(i()) }
    bindSingleton<EmbeddedServerSupportInteractor> { EmbeddedServerSupportInteractorImpl() }

    bindSingleton {
        val viewModelFactory = AddEmbeddedServerViewModelFactory(i())
        AddEmbeddedServerScreenFactory(viewModelFactory)
    }
}

internal expect fun Modules.featureEmbeddedServerPlatform(): DI.Module
