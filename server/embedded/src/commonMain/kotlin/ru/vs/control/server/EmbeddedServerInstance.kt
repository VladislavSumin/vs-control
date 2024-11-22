package ru.vs.control.server

import ru.vs.control.server.web.WebServerImpl

class EmbeddedServerInstance {
    private val webServer = WebServerImpl()
    suspend fun run() {
        webServer.run()
    }
}
