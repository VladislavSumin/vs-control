package ru.vs.control.feature.serverInfo.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApi
import ru.vs.control.feature.serverInfo.client.api.ServerInfoApiImpl
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServerInfo() = DI.Module("feature-server-info") {
    bindSingleton<ServerInfoApi> { ServerInfoApiImpl(i()) }
    bindSingleton<ServerInfoInteractor> { ServerInfoInteractorImpl(i()) }
}
