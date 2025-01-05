package ru.vs.control.entities.dto

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.entities.domain.ExternalEntityStateSerializer
import ru.vs.control.entities.domain.baseEntityStates.BooleanEntityState

/**
 * Factory to create [SerializersModule] for polymorphic [EntityState] serialization.
 */
internal interface EntityStateSerializerModuleFactory {
    fun create(): SerializersModule
}

internal class EntityStateSerializerModuleFactoryImpl(
    private val externalEntityStateSerializers: Set<ExternalEntityStateSerializer<EntityState>>,
) : EntityStateSerializerModuleFactory {
    override fun create(): SerializersModule {
        return SerializersModule {
            polymorphic(EntityState::class) {
                registerBaseTypes()
                registerExternalTypes()
            }
        }
    }

    /**
     * Register base set of [EntityState].
     */
    private fun PolymorphicModuleBuilder<EntityState>.registerBaseTypes() {
        subclass(BooleanEntityState::class)
    }

    /**
     * Register external [EntityState] types provides from other modules, see [ExternalEntityStateSerializer].
     */
    private fun PolymorphicModuleBuilder<EntityState>.registerExternalTypes() {
        externalEntityStateSerializers.forEach {
            subclass(it.kClass, it.kSerializer)
        }
    }
}
