package ru.vs.control.feature.entities.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.entities.featureEntitiesShared
import ru.vs.control.feature.entities.rsub.EntitiesRsubRSubServerProxy
import ru.vs.control.feature.entities.server.domain.EntitiesInteractor
import ru.vs.control.feature.entities.server.domain.EntitiesInteractorImpl
import ru.vs.control.feature.entities.server.repository.EntitiesRegistry
import ru.vs.control.feature.entities.server.repository.EntitiesRegistryImpl
import ru.vs.control.feature.entities.server.rsub.EntitiesRsubImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.rsub.server.di.bindRsubServerInterface

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSingleton<EntitiesRegistry> { EntitiesRegistryImpl() }
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }
    bindRsubServerInterface {
        val entities = EntitiesRsubImpl(i())
        EntitiesRsubRSubServerProxy(entities)
    }
}
