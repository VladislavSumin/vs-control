package ru.vs.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

/**
 * Заменяет null на [value] в потоке.
 */
fun <T : Any> Flow<T?>.defaultValue(value: T): Flow<T> = map { it ?: value }

/**
 * Стандартный share.
 * Поддерживает подписку пока есть хотя бы один подписчик, повторяет последнее состояние для новых подписчиков.
 */
fun <T> Flow<T>.share(scope: CoroutineScope): SharedFlow<T> = shareIn(
    scope,
    SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
    replay = 1,
)
