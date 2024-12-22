package ru.vs.rsub.client_server

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
        val channel1 = Channel<String>()
        val channel2 = Channel<String>()

        val connection1 = Connection(channel1, channel2)
        val connection2 = Connection(channel2, channel1)

        scope.launch {
            server.handleNewConnection(connection1)
        }

        return connection2
    }

    private class Connection(
        private val receiveChannel: ReceiveChannel<String>,
        private val sendChannel: SendChannel<String>
    ) : RSubConnection {
        override val receive: Flow<String> = receiveChannel.receiveAsFlow()

        override suspend fun send(data: String) {
            sendChannel.send(data)
        }

        override suspend fun close() {
            sendChannel.close()
            receiveChannel.cancel()
        }
    }
}
