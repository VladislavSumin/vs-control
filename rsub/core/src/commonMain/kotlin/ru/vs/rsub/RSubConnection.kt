package ru.vs.rsub

import kotlinx.coroutines.flow.Flow

interface RSubConnection {
    val receive: Flow<String>
    suspend fun send(data: String)
    suspend fun close()
}
