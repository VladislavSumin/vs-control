package ru.vs.core.properties

import kotlin.reflect.KType
import kotlin.reflect.typeOf

abstract class Properties {
    /**
     * Возвращает [Property] по переданному ключу [propertyKey].
     * @param defaultValue значение по умолчанию.
     */
    inline fun <reified T : Any> getProperty(propertyKey: PropertyKey, defaultValue: T): Property<T> =
        getPropertyInternal(propertyKey, defaultValue, typeOf<T>())

    /**
     * Возвращает nullable [Property] по переданному ключу [propertyKey].
     */
    inline fun <reified T : Any> getProperty(propertyKey: PropertyKey): Property<T?> =
        getPropertyInternal(propertyKey, null, typeOf<T?>())

    /**
     * Хак для возможности передать [typeOf] в protected [getProperty] не раскрывая эту функцию для внешнего вызова.
     */
    @PublishedApi
    internal fun <T> getPropertyInternal(
        propertyKey: PropertyKey,
        defaultValue: T,
        type: KType,
    ): Property<T> = getProperty(propertyKey, defaultValue, type)

    protected abstract fun <T> getProperty(propertyKey: PropertyKey, defaultValue: T, type: KType): Property<T>
}
