package ru.vs.core.coroutines

import kotlinx.coroutines.Dispatchers

internal actual val IoDispatcher: kotlinx.coroutines.CoroutineDispatcher
    get() = Dispatchers.Default
