package ru.vs.rsub.connector.ktorWebsocket

import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubException

class RSubConnectionKtorWebSocket(
    private val session: DefaultWebSocketSession,
) : RSubConnection {
    override val receive: Flow<ByteArray>
        get() = session.incoming.receiveAsFlow()
            .map { it as Frame.Binary }
            .map { it.data }
            .catch { exception ->
                throw when (exception) {
                    // TODO в новом ktor больше нет этой ошибки, разобраться
//                    is EOFException -> RSubExpectedExceptionOnConnectionException(
//                        exception.message ?: "Expected exception while receiving messages",
//                        exception,
//                    )

                    else -> RSubException("Unknown exception while receiving message", exception)
                }
            }

    override suspend fun send(data: ByteArray) = session.send(Frame.Binary(true, data))

    override suspend fun close() = session.close()
}
