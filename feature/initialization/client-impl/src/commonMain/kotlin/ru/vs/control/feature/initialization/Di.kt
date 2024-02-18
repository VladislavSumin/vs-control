package ru.vs.control.feature.initialization

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.initialization.domain.InitializationInteractor
import ru.vs.control.feature.initialization.domain.InitializationInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureInitialization() = DI.Module("feature-initialization") {
    bindSingleton<InitializationInteractor> { InitializationInteractorImpl(this, i()) }
}
