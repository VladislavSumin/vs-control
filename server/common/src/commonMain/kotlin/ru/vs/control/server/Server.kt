package ru.vs.control.server

import org.kodein.di.instance
import ru.vs.control.server.web.WebServer

class Server {
    val di = createDi()

    suspend fun run() {
        val webServer: WebServer by di.instance()
        webServer.run()
    }
}
