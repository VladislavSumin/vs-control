@file:Suppress("MagicNumber")

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.routing.routing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault
import ru.vs.rsub.RSubServer
import ru.vs.rsub.connector.ktorWebsocket.RSubConnectorKtorWebSocket
import ru.vs.rsub.connector.ktorWebsocket.rSubWebSocket
import ru.vs.rsub.playground.TestClient
import ru.vs.rsub.playground.TestClientImpl
import ru.vs.rsub.playground.TestInterfaceImpl
import ru.vs.rsub.playground.TestServerSubscriptionsImpl

fun main() {
    runClientSever()
}

private fun runClientSever() {
    LoggerManager.initDefault()
    val server = startServer()
    val httpClient = createHttpClient()
    val rSubClient = createRSubClient(httpClient)

    runBlocking {
        withContext(Dispatchers.IO) {
            //        val job = launch {
            //            rSubClient.observeConnection().collect {
            //                println("New status $it")
            //            }
            //        }

            println(rSubClient.test.testString())
        }
    }

    httpClient.close()
    server.stop(100L, 100L)
}

private fun startServer(): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
    val impls = TestServerSubscriptionsImpl(TestInterfaceImpl())
    val rSubServer = RSubServer(impls)
    return embeddedServer(
        factory = CIO,
        port = 8080,
        module = {
            install(io.ktor.server.websocket.WebSockets)
            routing {
                rSubWebSocket(rSubServer)
            }
        },
    ).start(false)
}

private fun createHttpClient(): HttpClient {
    return HttpClient {
        install(WebSockets)
    }
}

private fun createRSubClient(client: HttpClient): TestClient {
    return TestClientImpl(RSubConnectorKtorWebSocket(client))
}
