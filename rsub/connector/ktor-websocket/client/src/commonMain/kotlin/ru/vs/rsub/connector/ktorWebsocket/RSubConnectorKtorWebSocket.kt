package ru.vs.rsub.connector.ktorWebsocket

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.utils.io.errors.IOException
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector
import ru.vs.rsub.RSubException
import ru.vs.rsub.RSubExpectedExceptionOnConnectionException

class RSubConnectorKtorWebSocket(
    private val client: HttpClient,
    private val block: HttpRequestBuilder.() -> Unit,
) : RSubConnector {

    constructor(
        client: HttpClient,
        protocol: URLProtocol = URLProtocol.WS,
        host: String = "localhost",
        path: String = "/rSub",
        port: Int = 8080,
    ) : this(client, {
        url {
            this.protocol = protocol
            this.host = host
            this.path(path)
            this.port = port
        }
    })

    @Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
    override suspend fun connect(): RSubConnection {
        try {
            val session = client.webSocketSession {
                method = HttpMethod.Get
                setAttributes { header(HttpHeaders.SecWebSocketProtocol, "rSub") }
                block()
            }
            return RSubConnectionKtorWebSocket(session)
        } catch (e: Exception) {
            throw when {
                e is IOException || e.isPlatformExpectedException() -> {
                    RSubExpectedExceptionOnConnectionException("Catch checked exception while connect", e)
                }

                else -> RSubException("Unknown exception while connect", e)
            }
        }
    }
}
