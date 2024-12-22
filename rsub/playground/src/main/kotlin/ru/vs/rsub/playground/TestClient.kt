package ru.vs.rsub.playground

import ru.vs.rsub.RSubClient

@RSubClient
interface TestClient {
    val test: TestInterface
}
