package ru.vs.control.feature.rsub.server.module

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import kotlinx.serialization.protobuf.ProtoBuf
import ru.vs.core.ktor.server.KtorServerModule
import ru.vs.rsub.RSubServer
import ru.vs.rsub.connector.ktorWebsocket.rSubWebSocket

internal class RSubModule(
    private val protobuf: ProtoBuf,
) : KtorServerModule {
    override fun Application.module() {
        install(WebSockets)

        val rSubServer = RSubServer(
            rSubProxies = emptySet(),
            protobuf = protobuf,
        )

        routing {
            rSubWebSocket(rSubServer)
        }
    }
}
