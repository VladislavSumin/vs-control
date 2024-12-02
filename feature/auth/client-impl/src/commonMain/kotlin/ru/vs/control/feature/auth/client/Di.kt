package ru.vs.control.feature.auth.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.auth.client.api.ServerAuthApi
import ru.vs.control.feature.auth.client.api.ServerAuthApiImpl
import ru.vs.control.feature.auth.client.domain.ServerAuthInteractor
import ru.vs.control.feature.auth.client.domain.ServerAuthInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureAuth() = DI.Module("feature-auth") {
    bindSingleton<ServerAuthApi> { ServerAuthApiImpl(i()) }
    bindSingleton<ServerAuthInteractor> { ServerAuthInteractorImpl(i()) }
}
