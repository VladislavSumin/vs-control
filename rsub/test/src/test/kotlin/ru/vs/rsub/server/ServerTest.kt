package ru.vs.rsub.server

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.vs.rsub.RSubMessage

class ServerTest : BaseServerTest() {
    @Test
    fun `success call suspend function with unit return type`(): Unit = runBlocking {
        testSuspend("unitSuspend", testInterface::unitSuspend)
    }

    @Test
    fun `success call suspend function with string return type`(): Unit = runBlocking {
        testSuspend("stringSuspend", testInterface::stringSuspend)
    }

    @Test
    fun `success call suspend function with int return type`(): Unit = runBlocking {
        testSuspend("intSuspend", testInterface::intSuspend)
    }

    @Test
    fun `success call suspend function with long return type`(): Unit = runBlocking {
        testSuspend("longSuspend", testInterface::longSuspend)
    }

    @Test
    fun `success call suspend function with list string return type`(): Unit = runBlocking {
        testSuspend("listStringSuspend", testInterface::listStringSuspend)
    }

    @Test
    fun `success call suspend function with set string return type`(): Unit = runBlocking {
        testSuspend("setStringSuspend", testInterface::setStringSuspend)
    }

    @Test
    fun `success call suspend function with map string string return type`(): Unit = runBlocking {
        testSuspend("mapStringStringSuspend", testInterface::mapStringStringSuspend)
    }

    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    private suspend inline fun <reified T> testSuspend(methodName: String, expected: suspend () -> T) {
        initConnection()
        sendChannel.send(getSubscribeMessage(methodName))
        val response = parseResponse<T>()
        assertEquals(expected(), response)
    }

    private suspend inline fun <reified T> parseResponse(): T {
        val rawResponse = receiveChannel.receive()
        val responseMsg = ProtoBuf.decodeFromByteArray<RSubMessage>(rawResponse) as RSubMessage.RSubServerMessage.Data
        return ProtoBuf.decodeFromByteArray(responseMsg.data)
    }

    private fun getSubscribeMessage(functionName: String): ByteArray {
        val msg = RSubMessage.RSubClientMessage.Subscribe(0, "TestInterface", functionName, emptyList())
        return ProtoBuf.encodeToByteArray<RSubMessage>(msg)
    }
}
