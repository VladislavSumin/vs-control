package ru.vs.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Абстракция над [Dispatchers], для возможности подменять [CoroutineDispatcher] в тестах.
 */
interface DispatchersProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: MainCoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

internal object DefaultDispatchersProvider : DispatchersProvider {
    override val io: CoroutineDispatcher = IoDispatcher
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
    override val unconfined = Dispatchers.Unconfined
}

internal expect val IoDispatcher: CoroutineDispatcher
