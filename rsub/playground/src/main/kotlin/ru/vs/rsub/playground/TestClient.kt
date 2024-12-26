package ru.vs.rsub.playground

import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnector

class TestClient(connector: RSubConnector) : RSubClient(connector) {
    val test: TestInterface = TestInterfaceRSubImpl(this)
}
