package ru.vs.rsub.server

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubServer
import ru.vs.rsub.TestInterfaceImpl
import ru.vs.rsub.connection.createTestConnection
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Timeout(10, unit = TimeUnit.SECONDS)
open class BaseServerTest {
    protected val testInterface = TestInterfaceImpl()
    private val testServerSubscriptions = TestServerSubscriptionsImpl(testInterface)

    lateinit var sendChannel: SendChannel<String>
    lateinit var receiveChannel: ReceiveChannel<String>
    lateinit var connection: RSubConnection

    private lateinit var scope: CoroutineScope
    private lateinit var server: RSubServer

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        scope = CoroutineScope(CoroutineName("test-scope"))
        server = RSubServer(testServerSubscriptions)

        val testConnection = createTestConnection()
        connection = testConnection.connection
        sendChannel = testConnection.sendChannel
        receiveChannel = testConnection.receiveChannel
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.coroutineContext.job.cancelAndJoin()
    }

    fun initConnection() {
        scope.launch { server.handleNewConnection(connection) }
    }
}
