package ru.vs.control.feature.initialization.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.initialization.client.domain.InitializationInteractor
import ru.vs.control.feature.initialization.client.domain.InitializationInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureInitialization() = DI.Module("feature-initialization") {
    bindSingleton<InitializationInteractor> { InitializationInteractorImpl(this, i()) }
}
