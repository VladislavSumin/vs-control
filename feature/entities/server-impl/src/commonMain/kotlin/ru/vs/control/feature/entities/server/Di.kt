package ru.vs.control.feature.entities.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.entities.featureEntitiesShared
import ru.vs.control.feature.entities.rsub.EntitiesRsub
import ru.vs.control.feature.entities.server.domain.EntitiesInteractor
import ru.vs.control.feature.entities.server.domain.EntitiesInteractorImpl
import ru.vs.control.feature.entities.server.repository.EntitiesRegistry
import ru.vs.control.feature.entities.server.repository.EntitiesRegistryImpl
import ru.vs.control.feature.entities.server.rsub.EntitiesRsubImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSingleton<EntitiesRsub> { EntitiesRsubImpl(i()) }
    bindSingleton<EntitiesRegistry> { EntitiesRegistryImpl() }
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }
}
