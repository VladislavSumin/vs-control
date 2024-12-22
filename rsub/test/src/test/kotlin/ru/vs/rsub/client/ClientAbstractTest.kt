package ru.vs.rsub.client

import app.cash.turbine.test
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.RSubExpectedExceptionOnConnectionException

class ClientAbstractTest : BaseClientTest() {

    @Test
    fun `normal connect and then disconnect`(): Unit = runBlocking {
        client.observeConnectionStatus().test {
            assertEquals(RSubConnectionStatus.CONNECTING, awaitItem())
            assertEquals(RSubConnectionStatus.CONNECTED, awaitItem())
            cancel()
        }

        scope.cancel()
        scope.coroutineContext.job.join()

        verify(connector, times(1)).connect()
        verify(connection, times(1)).receive
        verify(connection, times(0)).send(any())
        verify(connection, times(1)).close()
    }

    @Test
    fun `fail at first connection try and success on reconnect`(): Unit = runBlocking {
        whenever(connector.connect())
            .doAnswer { throw RSubExpectedExceptionOnConnectionException("test exception", Exception()) }
            .doReturn(connection)

        client.observeConnectionStatus().test {
            assertEquals(RSubConnectionStatus.CONNECTING, awaitItem())
            assertEquals(RSubConnectionStatus.DISCONNECTED, awaitItem())
            assertEquals(RSubConnectionStatus.CONNECTING, awaitItem())
            assertEquals(RSubConnectionStatus.CONNECTED, awaitItem())
            cancel()
        }

        scope.cancel()
        scope.coroutineContext.job.join()

        verify(connector, times(2)).connect()
        verify(connection, times(1)).receive
        verify(connection, times(0)).send(any())
        verify(connection, times(1)).close()
    }
}
