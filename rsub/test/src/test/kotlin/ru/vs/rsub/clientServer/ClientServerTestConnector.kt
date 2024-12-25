package ru.vs.rsub.clientServer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector
import ru.vs.rsub.RSubServer

class ClientServerTestConnector(private val server: RSubServer, private val scope: CoroutineScope) : RSubConnector {
    override suspend fun connect(): RSubConnection {
        val channel1 = Channel<ByteArray>()
        val channel2 = Channel<ByteArray>()

        val connection1 = Connection(channel1, channel2)
        val connection2 = Connection(channel2, channel1)

        scope.launch {
            server.handleNewConnection(connection1)
        }

        return connection2
    }

    private class Connection(
        private val receiveChannel: ReceiveChannel<ByteArray>,
        private val sendChannel: SendChannel<ByteArray>,
    ) : RSubConnection {
        override val receive: Flow<ByteArray> = receiveChannel.receiveAsFlow()

        override suspend fun send(data: ByteArray) {
            sendChannel.send(data)
        }

        override suspend fun close() {
            sendChannel.close()
            receiveChannel.cancel()
        }
    }
}
