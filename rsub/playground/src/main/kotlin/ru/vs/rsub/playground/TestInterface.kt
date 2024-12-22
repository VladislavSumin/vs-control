package ru.vs.rsub.playground

import ru.vs.rsub.RSubInterface

@RSubInterface
interface TestInterface {
    suspend fun testUnit()
    suspend fun testUnit37()
    suspend fun testString(): String
}
