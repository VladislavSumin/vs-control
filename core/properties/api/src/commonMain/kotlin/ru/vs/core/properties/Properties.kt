package ru.vs.core.properties

import kotlin.reflect.KProperty

interface Properties {
    /**
     * Возвращает [Property] по переданному ключу [propertyKey].
     */
    fun <T : Any> getProperty(propertyKey: PropertyKey): Property<T>

    /**
     * Делегат для доступа к [Properties] с использованием в качестве ключа названия поля.
     *
     * **Обратите внимание!** При изменении названия поля изменится и ключ.
     *
     */
    operator fun <T : Any> getValue(thisRef: Any?, property: KProperty<*>): Property<T> {
        return getProperty(PropertyKey(property.name))
    }
}
