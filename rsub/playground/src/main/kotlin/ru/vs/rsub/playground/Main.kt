@file:Suppress("MagicNumber")

fun main() {
    runClientSever()
}

private fun runClientSever() {
//    val server = startServer()
//    val httpClient = createHttpClient()
//    val rSubClient = createRSubClient(httpClient)
//
//    runBlocking {
//        withContext(Dispatchers.IO) {
////        val job = launch {
////            rSubClient.observeConnection().collect {
////                println("New status $it")
////            }
////        }
//
//            println(rSubClient.test.testString())
//        }
//    }
//
//    httpClient.close()
//    server.stop(100L, 100L)
}

//private fun startServer(): ApplicationEngine {
//    val impls = TestServerSubscriptionsImpl(TestInterfaceImpl())
//    val rSubServer = RSubServer(impls)
//    return embeddedServer(CIO, port = 8080) {
//        install(WebSockets)
//        routing {
//            rSubWebSocket(rSubServer)
//        }
//    }.start(false)
//}
//
//private fun createHttpClient(): HttpClient {
//    return HttpClient {
//        install(io.ktor.client.plugins.websocket.WebSockets)
//    }
//}
//
//private fun createRSubClient(client: HttpClient): TestClient {
//    return TestClientImpl(RSubConnectorKtorWebSocket(client))
//}
