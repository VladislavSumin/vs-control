package ru.vs.control.server

import org.kodein.di.instance
import ru.vs.control.server.web.WebServer

/**
 * Точка входа для запуска сервера.
 */
class Server {
    val di = createDi()

    /**
     * Запускает сервер, при отмене корутины корректно завершает работу сервера.
     */
    suspend fun run(): Nothing {
        val webServer: WebServer by di.instance()
        webServer.run()
    }
}
