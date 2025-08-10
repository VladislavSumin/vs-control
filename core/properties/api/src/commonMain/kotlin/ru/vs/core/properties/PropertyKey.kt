package ru.vs.core.properties

/**
 * Ключ для доступа к [Property].
 *
 * @param key сырой идентификатор ключа.
 */
class PropertyKey<T>(val type: PropertyType<T>, val key: String)

sealed interface PropertyType<T> {
    data object Boolean : PropertyType<kotlin.Boolean>
}
