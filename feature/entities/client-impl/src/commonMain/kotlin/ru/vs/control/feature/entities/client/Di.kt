package ru.vs.control.feature.entities.client

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.control.feature.entities.client.domain.EntitiesInteractor
import ru.vs.control.feature.entities.client.domain.EntitiesInteractorImpl
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactory
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistry
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistryImpl
import ru.vs.control.feature.entities.featureEntitiesShared
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSet<EntityStateComponentFactory<*>>()
//    inBindSet<EntityStateComponentFactory<*>> {
//        add { singleton { BooleanEntityStateComponentFactory() } }
//    }

    // Interactors
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }

    // View model factories
//    bindSingleton<EntitiesViewModelFactory> { EntitiesViewModelFactoryImpl(i()) }

    // Component factories
//    bindSingleton<EntitiesComponentFactory> { EntitiesComponentFactoryImpl(i(), i()) }
//    bindSingleton<EntitiesScreenComponentFactory> { EntitiesScreenComponentFactoryImpl(i()) }

    // Other
    bindSingleton<EntityStateComponentFactoryRegistry> { EntityStateComponentFactoryRegistryImpl(i()) }
}
