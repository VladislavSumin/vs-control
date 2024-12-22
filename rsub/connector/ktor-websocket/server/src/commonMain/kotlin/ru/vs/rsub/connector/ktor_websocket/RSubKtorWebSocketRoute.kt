package ru.vs.rsub.connector.ktor_websocket

import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import ru.vs.rsub.RSubServer

fun Route.rSubWebSocket(server: RSubServer, path: String = "/rSub") {
    webSocket(path = path, protocol = "rSub") {
        server.handleNewConnection(RSubConnectionKtorWebSocket(this))
    }
}
