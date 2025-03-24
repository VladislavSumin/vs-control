package ru.vs.rsub.client

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.serialization.protobuf.ProtoBuf
import ru.vladislavsumin.core.logger.api.logger
import ru.vladislavsumin.core.logger.common.Logger
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubConnector
import ru.vs.rsub.TestInterface
import ru.vs.rsub.TestInterfaceRSubImpl

class TestClient(
    connector: RSubConnector,
    reconnectInterval: Long = 3000,
    connectionKeepAliveTime: Long = 6000,
    protobuf: ProtoBuf = ProtoBuf,
    scope: CoroutineScope = GlobalScope,
    logger: Logger = logger("RSubClient"),
) : RSubClient(connector, reconnectInterval, connectionKeepAliveTime, protobuf, scope, logger) {
    val testInterface: TestInterface = TestInterfaceRSubImpl(this)
}
