package ru.vs.core.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CachingStateProcessingTest {
    /**
     * Проверяем что при отмене подписки все корректно отменяется.
     */
    @Test
    fun testCorrectProcessingCancellation() = runTest {
        val state = MutableStateFlow(listOf("A", "B", "C"))

        var counter = 0

        val job = launch {
            coroutineScope {
                state.cachingStateProcessing(
                    keySelector = { it },
                ) {
                    try {
                        counter++
                        delay(Long.MAX_VALUE)
                        emit(it)
                    } finally {
                        withContext(NonCancellable) {
                            counter--
                        }
                    }
                }
                    .stateIn(this)
            }
        }

        assertEquals(0, counter)
        runCurrent()
        assertEquals(3, counter)
        job.cancel()
        runCurrent()
        assertEquals(0, counter)
    }

    /**
     * Проверяем что старые подписки не перезапускаются при добавлении нового элемента
     */
    @Test
    fun testCorrectProcessingAddNewElement() = runTest {
        val state = MutableStateFlow(listOf("A", "B", "C"))

        var counter = 0

        val job = launch {
            coroutineScope {
                state.cachingStateProcessing(
                    keySelector = { it },
                ) {
                    counter++
                    delay(Long.MAX_VALUE)
                    emit(it)
                }
                    .stateIn(this)
            }
        }

        assertEquals(0, counter)
        runCurrent()
        assertEquals(3, counter)
        state.value = listOf("A", "NEW_ONE", "B", "C")
        runCurrent()
        assertEquals(4, counter)
        job.cancel()
    }

    /**
     * Проверяем что подписка старого элемента отменяется при его удалении
     */
    @Test
    fun testCorrectProcessingRemoveNewElement() = runTest {
        val state = MutableStateFlow(listOf("A", "B", "C"))

        var counter = 0
        var cancelCounter = 0

        val job = launch {
            coroutineScope {
                state.cachingStateProcessing(
                    keySelector = { it },
                ) {
                    try {
                        counter++
                        delay(Long.MAX_VALUE)
                        emit(it)
                    } finally {
                        cancelCounter++
                    }
                }
                    .stateIn(this)
            }
        }

        assertEquals(0, counter)
        runCurrent()
        assertEquals(3, counter)
        state.value = listOf("A", "C")
        runCurrent()
        assertEquals(3, counter)
        assertEquals(1, cancelCounter)
        job.cancel()
    }
}
