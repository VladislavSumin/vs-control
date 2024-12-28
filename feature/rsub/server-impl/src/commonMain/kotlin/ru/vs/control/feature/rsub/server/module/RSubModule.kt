package ru.vs.control.feature.rsub.server.module

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import ru.vs.core.ktor.server.KtorServerModule
import ru.vs.rsub.RSubServer
import ru.vs.rsub.connector.ktorWebsocket.rSubWebSocket

internal class RSubModule : KtorServerModule {
    override fun Application.module() {
        val rSubServer = RSubServer(emptySet())
        install(WebSockets)
        routing {
            rSubWebSocket(rSubServer)
        }
    }
}
