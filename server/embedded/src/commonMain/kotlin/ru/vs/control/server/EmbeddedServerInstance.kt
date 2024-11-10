package ru.vs.control.server

import io.ktor.server.cio.CIO
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class EmbeddedServerInstance {
    suspend fun run() {
        // TODO отключать сервер при отмене корутины.
        // TODO убрать дублирование кода.
        embeddedServer(
            factory = CIO,
            configure = {
                connector {
                    host = "0.0.0.0"
                    port = DEFAULT_SERVER_PORT
                }
            },
            module = {
                routing {
                    get("/info") { call.respondText("Hello") }
                }
            },
        )
            .start(true)
    }
}

private const val DEFAULT_SERVER_PORT = 8080
