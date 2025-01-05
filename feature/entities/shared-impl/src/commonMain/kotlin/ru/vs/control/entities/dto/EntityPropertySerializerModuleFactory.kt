package ru.vs.control.entities.dto

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.control.entities.domain.EntityProperty
import ru.vs.control.entities.domain.ExternalEntityPropertySerializer
import ru.vs.control.entities.domain.baseEntityProperties.DefaultNameEntityProperty

/**
 * Factory to create [SerializersModule] for polymorphic [EntityProperty] serialization.
 */
internal interface EntityPropertySerializerModuleFactory {
    fun create(): SerializersModule
}

internal class EntityPropertySerializerModuleFactoryImpl(
    private val externalEntityPropertySerializers: Set<ExternalEntityPropertySerializer<EntityProperty>>,
) : EntityPropertySerializerModuleFactory {
    override fun create(): SerializersModule {
        return SerializersModule {
            polymorphic(EntityProperty::class) {
                registerBaseTypes()
                registerExternalTypes()
            }
        }
    }

    /**
     * Register base set of [EntityProperty].
     */
    private fun PolymorphicModuleBuilder<EntityProperty>.registerBaseTypes() {
        subclass(DefaultNameEntityProperty::class)
    }

    /**
     * Register external [EntityProperty] types provides from other modules, see [ExternalEntityPropertySerializer].
     */
    private fun PolymorphicModuleBuilder<EntityProperty>.registerExternalTypes() {
        externalEntityPropertySerializers.forEach {
            subclass(it.kClass, it.kSerializer)
        }
    }
}
