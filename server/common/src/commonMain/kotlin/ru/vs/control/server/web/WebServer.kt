package ru.vs.control.server.web

import io.ktor.serialization.kotlinx.protobuf.protobuf
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.serverConfig
import io.ktor.server.engine.EngineSSLConnectorBuilder
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import ru.vs.control.server.domain.KeyStoreInteractor

/**
 * Осуществляет запуск веб сервера.
 */
internal interface WebServer {
    /**
     * Запускает и удерживает сервер в запущенном состоянии до отмены корутины.
     */
    suspend fun run()
}

internal class WebServerImpl(
    private val keyStoreInteractor: KeyStoreInteractor,
) : WebServer {
    override suspend fun run(): Unit = withContext(CoroutineName("web-server")) {
        val server = createEmbeddedServer()
        suspendCancellableCoroutine { continuation ->
            server.start()
            continuation.invokeOnCancellation { server.stop() }
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

    private fun CoroutineScope.createEmbeddedServer() = embeddedServer(
        factory = Netty,
        rootConfig = createServerConfig(),
    ) {
        connectors.add(
            EngineSSLConnectorBuilder(
                keyStore = keyStoreInteractor.createKeyStore(),
                keyAlias = "server",
                keyStorePassword = { "".toCharArray() },
                privateKeyPassword = { "".toCharArray() },
            ),
        )
    }

    private fun CoroutineScope.createServerConfig() = serverConfig(createEnvironment()) {
        parentCoroutineContext = coroutineContext + parentCoroutineContext
        watchPaths = emptyList()
        module { module() }
    }

    private fun createEnvironment() = applicationEnvironment {}
}

@Serializable
data class Info(
    val name: String,
    val version: String,
)
