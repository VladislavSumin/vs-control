package ru.vs.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.ContinuationInterceptor

/**
 * Устанавливает dispatcher [TestScope] в качестве Main dispatcher.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun TestScope.setMain() {
    val dispatcher = coroutineContext[ContinuationInterceptor.Key]!! as CoroutineDispatcher
    Dispatchers.setMain(dispatcher)
}
