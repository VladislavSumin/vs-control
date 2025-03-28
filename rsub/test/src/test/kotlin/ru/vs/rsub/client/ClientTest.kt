package ru.vs.rsub.client

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import ru.vs.rsub.RSubMessage

class ClientTest : BaseClientTest() {
    @Test
    fun `success call suspend function with unit return type`(): Unit = runBlocking {
        testSuspend(Unit, client.testInterface::unitSuspend)
    }

    @Test
    fun `success call suspend function with string return type`(): Unit = runBlocking {
        testSuspend("test", client.testInterface::stringSuspend)
    }

    @Test
    fun `success call suspend function with int return type`(): Unit = runBlocking {
        testSuspend(123456, client.testInterface::intSuspend)
    }

    @Test
    fun `success call suspend function with long return type`(): Unit = runBlocking {
        testSuspend(123456789L, client.testInterface::longSuspend)
    }

    @Test
    fun `success call suspend function with list string return type`(): Unit = runBlocking {
        testSuspend(listOf("test1", "test2", "test3"), client.testInterface::listStringSuspend)
    }

    @Test
    fun `success call suspend function with set string return type`(): Unit = runBlocking {
        testSuspend(setOf("test1", "test2", "test3"), client.testInterface::setStringSuspend)
    }

    @Test
    fun `success call suspend function with map string string return type`(): Unit = runBlocking {
        testSuspend(mapOf("testKey1" to "test1", "testKey2" to "test2"), client.testInterface::mapStringStringSuspend)
    }

    private suspend inline fun <reified T : Any> testSuspend(
        testData: T,
        noinline method: suspend () -> T,
    ) = coroutineScope {
        val resultDeferred = async { method() }

        val rawSubscribeMessage = receiveChannel.receive()
        val subscribeMessage = ProtoBuf.decodeFromByteArray<RSubMessage>(rawSubscribeMessage)
        assertInstanceOf(RSubMessage.RSubClientMessage.Subscribe::class.java, subscribeMessage)
        assertEquals(0, (subscribeMessage as RSubMessage.RSubClientMessage.Subscribe).id)

        val message = RSubMessage.RSubServerMessage.Data(0, ProtoBuf.encodeToByteArray(testData))
        sendChannel.send(ProtoBuf.encodeToByteArray<RSubMessage>(message))

        val result = resultDeferred.await()
        assertEquals(testData, result)
    }
}
