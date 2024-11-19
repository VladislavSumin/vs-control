package ru.vs.core.properties

import kotlinx.coroutines.flow.Flow

/**
 * Предоставляет доступ к property.
 *
 * @param T тип данных, должен быть примитивом или @Serializable объектом.
 */
interface Property<T> : Flow<T> {
    /**
     * Устанавливает состояние property.
     */
    suspend fun set(value: T)
}
