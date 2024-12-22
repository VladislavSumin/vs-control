package ru.vs.rsub

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class TestInterfaceImpl : TestInterface {
    val isInfinityFlowActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override suspend fun unitSuspend() {
        // no action
    }

    override suspend fun stringSuspend(): String {
        return "string"
    }

    override suspend fun intSuspend(): Int {
        return 1245678
    }

    override suspend fun longSuspend(): Long {
        return 1234567890L
    }

    override suspend fun listStringSuspend(): List<String> {
        return listOf("string1", "string2", "string3")
    }

    override suspend fun setStringSuspend(): Set<String> {
        return setOf("string1", "string2", "string3")
    }

    override suspend fun mapStringStringSuspend(): Map<String, String> {
        return mapOf("key1" to "string1", "key2" to "string2", "key3" to "string3")
    }

    override fun stringFlow(): Flow<String> {
        return flowOf("string1", "string2", "string3")
    }

    override fun listStringFlow(): Flow<List<String>> {
        return flowOf(
            listOf("string1", "string2", "string3"),
            listOf("string4", "string5", "string6"),
            listOf("string7", "string8", "string9")
        )
    }

    override fun infinityStringFlow(): Flow<String> {
        return flow {
            try {
                isInfinityFlowActive.emit(true)
                emit("string1")
                delay(Long.MAX_VALUE)
            } finally {
                isInfinityFlowActive.emit(false)
            }
        }
    }

    override fun infinityStringFlow2(): Flow<String> {
        return flow {
            emit("string1")
            delay(Long.MAX_VALUE)
        }
    }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun errorSuspend(): String {
        throw RuntimeException("errorSuspend")
    }

    @Suppress("TooGenericExceptionThrown")
    override fun errorFlow(): Flow<String> {
        return flow {
            emit("string1")
            throw RuntimeException("errorFlow")
        }
    }
}
