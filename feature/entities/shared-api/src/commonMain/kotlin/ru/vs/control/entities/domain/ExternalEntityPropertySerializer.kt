package ru.vs.control.entities.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Used to pass external serializer for custom [EntityProperty].
 *
 * Bind our custom entities serializer into root kodein module:
 * ```
 * inBindSet<ExternalEntityPropertySerializer<out EntityProperty>> {
 *     add { singleton { ExternalEntityPropertySerializer<ExampleEntityProperty>() } }
 * }
 *```
 *
 * @param kClass - custom entity property class.
 * @param kSerializer - serializer for [kClass] entity property.
 */
data class ExternalEntityPropertySerializer<T : EntityProperty>(
    val kClass: KClass<T>,
    val kSerializer: KSerializer<T>
) {
    companion object {
        inline operator fun <reified T : EntityProperty> invoke(): ExternalEntityPropertySerializer<T> {
            return ExternalEntityPropertySerializer(T::class, serializer())
        }
    }
}
