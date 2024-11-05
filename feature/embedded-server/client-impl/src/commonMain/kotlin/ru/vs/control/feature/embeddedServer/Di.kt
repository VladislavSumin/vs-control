package ru.vs.control.feature.embeddedServer

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractor
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServerSupportInteractorImpl
import ru.vs.core.di.Modules

fun Modules.featureEmbeddedServer() = DI.Module("feature-embedded-server") {
    bindSingleton<EmbeddedServerSupportInteractor> { EmbeddedServerSupportInteractorImpl() }
}
