package ru.vs.rsub.playground

class TestInterfaceImpl : TestInterface {
    override suspend fun testUnit() {
        // no action
    }

    override suspend fun testUnit37() {
        // no action
    }

    override suspend fun testString(): String {
        return "Hello"
    }
}
