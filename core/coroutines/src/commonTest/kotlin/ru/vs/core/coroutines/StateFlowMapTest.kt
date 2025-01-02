package ru.vs.core.coroutines

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StateFlowMapTest {

    @Test
    fun testGetMappedValue() {
        val state = MutableStateFlow(2).mapState { it.toString() }
        assertEquals("2", state.value)
    }

    @Test
    fun testGetMappedValueAsFlow() = runTest {
        val state = MutableStateFlow(2).mapState { it.toString() }
        val data = state.first()
        assertEquals("2", data)
    }

    @Test
    fun testGetMultipleMappedValueAsFlow() = runTest {
        val parentState = MutableStateFlow(2)
        val state = parentState.mapState { it.toString() }

        val list = mutableListOf<String>()

        val job = launch {
            state.collect { list.add(it) }
        }

        runCurrent()
        parentState.value = 3
        runCurrent()

        job.cancelAndJoin()

        assertEquals(listOf("2", "3"), list)
    }
}
