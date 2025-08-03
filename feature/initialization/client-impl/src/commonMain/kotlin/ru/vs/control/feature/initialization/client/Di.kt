package ru.vs.control.feature.initialization.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.initialization.client.domain.InitializationInteractor
import ru.vs.control.feature.initialization.client.domain.InitializationInteractorImpl

fun Modules.featureInitialization() = DI.Module("feature-initialization") {
    bindSingleton<InitializationInteractor> { InitializationInteractorImpl(this, i()) }
}
