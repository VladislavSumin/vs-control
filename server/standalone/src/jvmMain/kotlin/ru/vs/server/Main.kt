package ru.vs.server

import io.ktor.server.cio.CIO
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import ru.vs.core.logger.api.logger
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

fun main() {
    LoggerManager.initDefault()
    val logger = logger("main")
    logger.i { "Server hello" }

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

private const val DEFAULT_SERVER_PORT = 8080
