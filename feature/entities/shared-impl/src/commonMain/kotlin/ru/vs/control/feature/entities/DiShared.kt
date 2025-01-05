package ru.vs.control.feature.entities

import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.feature.entities.domain.EntityProperty
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.control.feature.entities.domain.ExternalEntityPropertySerializer
import ru.vs.control.feature.entities.domain.ExternalEntityStateSerializer
import ru.vs.control.feature.entities.dto.EntityPropertySerializerModuleFactory
import ru.vs.control.feature.entities.dto.EntityPropertySerializerModuleFactoryImpl
import ru.vs.control.feature.entities.dto.EntityStateSerializerModuleFactory
import ru.vs.control.feature.entities.dto.EntityStateSerializerModuleFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntitiesShared() = DI.Module("feature-entities-shared") {
    bindSet<ExternalEntityStateSerializer<out EntityState>>()
    bindSet<ExternalEntityPropertySerializer<out EntityProperty>>()

    bindSingleton<EntityStateSerializerModuleFactory> { EntityStateSerializerModuleFactoryImpl(i()) }
    bindSingleton<EntityPropertySerializerModuleFactory> { EntityPropertySerializerModuleFactoryImpl(i()) }

    // Register serializers module in default json parser instance
    inBindSet<SerializersModule> {
        add { singleton { i<EntityStateSerializerModuleFactory>().create() } }
        add { singleton { i<EntityPropertySerializerModuleFactory>().create() } }
    }
}
