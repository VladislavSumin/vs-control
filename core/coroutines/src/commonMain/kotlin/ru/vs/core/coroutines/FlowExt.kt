package ru.vs.core.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Заменяет null на [value] в потоке.
 */
fun <T : Any> Flow<T?>.defaultValue(value: T): Flow<T> = map { it ?: value }
