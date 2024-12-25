package ru.vs.rsub

import kotlinx.coroutines.flow.Flow

interface RSubConnection {
    val receive: Flow<ByteArray>
    suspend fun send(data: ByteArray)
    suspend fun close()
}
