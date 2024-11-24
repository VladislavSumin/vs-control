package ru.vs.control.server

class EmbeddedServerInstance {
    private val server = Server()
    suspend fun run() = server.run()
}
