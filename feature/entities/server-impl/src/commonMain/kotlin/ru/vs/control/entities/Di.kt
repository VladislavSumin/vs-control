package ru.vs.control.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.EntitiesInteractorImpl
import ru.vs.control.entities.repository.EntitiesRegistry
import ru.vs.control.entities.repository.EntitiesRegistryImpl
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.control.entities.rsub.EntitiesRsubImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSingleton<EntitiesRsub> { EntitiesRsubImpl(i()) }
    bindSingleton<EntitiesRegistry> { EntitiesRegistryImpl() }
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }
}
