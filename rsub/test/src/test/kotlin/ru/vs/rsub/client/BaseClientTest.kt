package ru.vs.rsub.client

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector
import ru.vs.rsub.connection.createTestConnection
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Timeout(10, unit = TimeUnit.SECONDS)
open class BaseClientTest {

    lateinit var sendChannel: SendChannel<String>

    lateinit var receiveChannel: ReceiveChannel<String>

    lateinit var connection: RSubConnection

    @Mock
    lateinit var connector: RSubConnector

    lateinit var scope: CoroutineScope

    internal lateinit var client: TestClientImpl

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        val testConnection = createTestConnection()
        connection = testConnection.connection
        sendChannel = testConnection.sendChannel
        receiveChannel = testConnection.receiveChannel

        reset(connector)
        whenever(connector.connect()) doReturn connection

        scope = CoroutineScope(CoroutineName("test-scope"))

        client = TestClientImpl(connector, scope = scope, connectionKeepAliveTime = 0, reconnectInterval = 0)
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.coroutineContext.job.cancelAndJoin()
    }
}
