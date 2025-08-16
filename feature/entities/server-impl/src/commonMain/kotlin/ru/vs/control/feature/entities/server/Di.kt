package ru.vs.control.feature.entities.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.entities.featureEntitiesShared
import ru.vs.control.feature.entities.server.domain.EntitiesInteractor
import ru.vs.control.feature.entities.server.domain.EntitiesInteractorImpl
import ru.vs.control.feature.entities.server.repository.EntitiesRegistry
import ru.vs.control.feature.entities.server.repository.EntitiesRegistryImpl

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSingleton<EntitiesRegistry> { EntitiesRegistryImpl() }
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }
// TODO починить ksp?
//    bindRsubServerInterface {
//        val entities = EntitiesRsubImpl(i())
//        EntitiesRsubRSubServerProxy(entities)
//    }
}
