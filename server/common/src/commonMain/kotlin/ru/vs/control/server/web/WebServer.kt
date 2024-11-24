package ru.vs.control.server.web

import io.ktor.serialization.kotlinx.protobuf.protobuf
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.serverConfig
import io.ktor.server.cio.CIO
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

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
            val environment = applicationEnvironment {}

            val config = serverConfig(environment) {
                parentCoroutineContext = coroutineContext + parentCoroutineContext
                watchPaths = emptyList()
                module { module() }
            }

            val server = embeddedServer(
                factory = CIO,
                rootConfig = config,
                configure = { connectors.add(EngineConnectorBuilder().apply { port = DEFAULT_SERVER_PORT }) },
            )

            // TODO отключать сервер при отмене корутины.
            server.start(true)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun Application.module() {
        install(ContentNegotiation) {
            protobuf()
        }
        routing {
            get("/info") {
                call.respond(Info("Test", "0.0.1"))
            }
        }
    }
}

@Serializable
data class Info(
    val name: String,
    val version: String,
)

private const val DEFAULT_SERVER_PORT = 8080
