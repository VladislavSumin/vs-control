package ru.vs.rsub.clientServer

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import ru.vs.rsub.RSubServer
import ru.vs.rsub.TestInterfaceImpl
import ru.vs.rsub.client.TestClient
import ru.vs.rsub.client.TestClientImpl
import ru.vs.rsub.server.TestServerSubscriptionsImpl
import java.util.concurrent.TimeUnit
import ru.vs.rsub.log.LoggerInit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Timeout(10, unit = TimeUnit.SECONDS)
open class ClientServerBaseTest {
    protected val testInterface = TestInterfaceImpl()
    private val testServerSubscriptions = TestServerSubscriptionsImpl(testInterface)

    private lateinit var scope: CoroutineScope
    private lateinit var server: RSubServer
    protected lateinit var client: TestClient
    private lateinit var connector: ClientServerTestConnector

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        LoggerInit
        scope = CoroutineScope(CoroutineName("test-scope"))
        server = RSubServer(testServerSubscriptions)
        connector = ClientServerTestConnector(server, scope)
        client = TestClientImpl(connector, scope = scope, connectionKeepAliveTime = 0, reconnectInterval = 0)
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.coroutineContext.job.cancelAndJoin()
    }
}
