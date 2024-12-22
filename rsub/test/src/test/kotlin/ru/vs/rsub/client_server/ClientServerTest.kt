package ru.vs.rsub.client_server

import app.cash.turbine.test
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.vs.rsub.RSubServerException

class ClientServerTest : ClientServerBaseTest() {
    @Test
    fun `success call suspend function with unit return type`(): Unit = runBlocking {
        assertEquals(testInterface.unitSuspend(), client.testInterface.unitSuspend())
    }

    @Test
    fun `success call suspend function with int return type`(): Unit = runBlocking {
        assertEquals(testInterface.intSuspend(), client.testInterface.intSuspend())
    }

    @Test
    fun `success call suspend function with long return type`(): Unit = runBlocking {
        assertEquals(testInterface.longSuspend(), client.testInterface.longSuspend())
    }

    @Test
    fun `success call suspend function with string return type`(): Unit = runBlocking {
        assertEquals(testInterface.stringSuspend(), client.testInterface.stringSuspend())
    }

    @Test
    fun `success call suspend function with list string return type`(): Unit = runBlocking {
        assertEquals(testInterface.listStringSuspend(), client.testInterface.listStringSuspend())
    }

    @Test
    fun `success call suspend function with set string return type`(): Unit = runBlocking {
        assertEquals(testInterface.setStringSuspend(), client.testInterface.setStringSuspend())
    }

    @Test
    fun `success call suspend function with map string string return type`(): Unit = runBlocking {
        assertEquals(testInterface.mapStringStringSuspend(), client.testInterface.mapStringStringSuspend())
    }

    @Test
    fun `success call flow function with string return type`(): Unit = runBlocking {
        assertEquals(testInterface.stringFlow().toList(), client.testInterface.stringFlow().toList())
    }

    @Test
    fun `success call flow function with list string return type`(): Unit = runBlocking {
        assertEquals(testInterface.listStringFlow().toList(), client.testInterface.listStringFlow().toList())
    }

    @Test
    fun `fail call suspend function with string return type`(): Unit = runBlocking {
        assertThrows<RSubServerException> { client.testInterface.errorSuspend() }
    }

    @Test
    fun `fail call flow function with string return type`(): Unit = runBlocking {
        client.testInterface.errorFlow().test {
            assertEquals("string1", awaitItem())
            assertInstanceOf(RSubServerException::class.java, awaitError())
        }
    }

    @Test
    fun `success call infinity flow`(): Unit = runBlocking {
        assertFalse(testInterface.isInfinityFlowActive.value)

        val connectionHolder = launch { testInterface.infinityStringFlow2().toList() }

        client.testInterface.infinityStringFlow().test {
            assertEquals("string1", awaitItem())
            assertTrue(testInterface.isInfinityFlowActive.value)
            cancel()
        }

        withTimeout(100) {
            testInterface.isInfinityFlowActive.first { !it }
        }
        assertFalse(testInterface.isInfinityFlowActive.value)

        connectionHolder.cancelAndJoin()
    }
}
