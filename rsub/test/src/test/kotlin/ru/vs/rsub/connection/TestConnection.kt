package ru.vs.rsub.connection

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.receiveAsFlow
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import ru.vs.rsub.RSubConnection

data class TestConnection(
    val connection: RSubConnection,
    val sendChannel: SendChannel<String>,
    val receiveChannel: ReceiveChannel<String>
)

fun createTestConnection(): TestConnection {
    val sendChannel = Channel<String>()
    val receiveChannel = Channel<String>()

    val connection = mock<RSubConnection> {
        on { receive } doReturn sendChannel.receiveAsFlow()
        onBlocking { send(any()) } doSuspendableAnswer { receiveChannel.send(it.arguments[0] as String) }
    }
    return TestConnection(connection, sendChannel, receiveChannel)
}
