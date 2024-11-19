package ru.vs.core.properties

import kotlinx.coroutines.flow.Flow

internal class PropertyImpl<T : Any>(
    private val flow: Flow<T>,
    private val setter: suspend (T) -> Unit,
) : Property<T>, Flow<T> by flow {
    override suspend fun set(value: T) = setter(value)
}
