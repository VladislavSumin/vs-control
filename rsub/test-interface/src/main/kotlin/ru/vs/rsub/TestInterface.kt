package ru.vs.rsub

import kotlinx.coroutines.flow.Flow

@RSubInterface
interface TestInterface {
    suspend fun unitSuspend(): Unit
    suspend fun stringSuspend(): String
    suspend fun intSuspend(): Int
    suspend fun longSuspend(): Long

    suspend fun listStringSuspend(): List<String>
    suspend fun setStringSuspend(): Set<String>
    suspend fun mapStringStringSuspend(): Map<String, String>

    fun stringFlow(): Flow<String>
    fun listStringFlow(): Flow<List<String>>

    fun infinityStringFlow(): Flow<String>
    fun infinityStringFlow2(): Flow<String>

    suspend fun errorSuspend(): String
    fun errorFlow(): Flow<String>
}
