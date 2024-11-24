package ru.vs.control.server.web

import io.ktor.server.cio.CIO
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.withContext

/**
 * Осуществляет запуск веб сервера.
 */
internal interface WebServer {
    /**
     * Запускает и удерживает сервер в запущенном состоянии до отмены корутины.
     */
    suspend fun run()
}

internal class WebServerImpl : WebServer {
    override suspend fun run() {
        withContext(CoroutineName("web-server")) {
            // TODO настроить логирование
            val server = embeddedServer(
                factory = CIO,
                connectors = arrayOf(EngineConnectorBuilder().apply { port = DEFAULT_SERVER_PORT }),
                watchPaths = emptyList(),
                module = {
                    routing {
                        get("/info") { call.respondText("Hello") }
                    }
                },
            )
            // TODO отключать сервер при отмене корутины.
            server.start(true)
        }
    }
}

private const val DEFAULT_SERVER_PORT = 8080
