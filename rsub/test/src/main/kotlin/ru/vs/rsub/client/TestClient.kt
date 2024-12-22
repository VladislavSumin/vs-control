package ru.vs.rsub.client

import ru.vs.rsub.RSubClient
import ru.vs.rsub.TestInterface

@RSubClient
interface TestClient {
    val testInterface: TestInterface
}
