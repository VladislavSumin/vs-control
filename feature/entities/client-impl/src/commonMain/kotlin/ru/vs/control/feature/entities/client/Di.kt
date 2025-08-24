package ru.vs.control.feature.entities.client

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.control.feature.entities.client.domain.EntitiesInteractor
import ru.vs.control.feature.entities.client.domain.EntitiesInteractorImpl
import ru.vs.control.feature.entities.client.ui.entities.EntitiesComponentFactory
import ru.vs.control.feature.entities.client.ui.entities.EntitiesComponentFactoryImpl
import ru.vs.control.feature.entities.client.ui.entities.EntitiesViewModelFactory
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactory
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistry
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistryImpl
import ru.vs.control.feature.entities.featureEntitiesShared

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSet<EntityStateComponentFactory<*>>()
//    inBindSet<EntityStateComponentFactory<*>> {
//        add { singleton { BooleanEntityStateComponentFactory() } }
//    }
    bindSingleton<EntityStateComponentFactoryRegistry> { EntityStateComponentFactoryRegistryImpl(i()) }

    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i(), i()) }

    bindSingleton<EntitiesComponentFactory> {
        val viewModel = EntitiesViewModelFactory(i())
        EntitiesComponentFactoryImpl(i(), viewModel)
    }
}
