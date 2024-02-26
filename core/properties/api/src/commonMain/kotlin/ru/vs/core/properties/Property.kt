package ru.vs.core.properties

import kotlinx.coroutines.flow.Flow

/**
 * Предоставляет доступ к property.
 *
 * @param T тип данных, должен быть примитивом или @Serializable объектом.
 */
interface Property<T : Any> {
    /**
     * Возвращает поток текущего состояния property.
     */
    val value: Flow<T?>

    /**
     * Устанавливает состояние property.
     */
    suspend fun set(value: T?)
}
