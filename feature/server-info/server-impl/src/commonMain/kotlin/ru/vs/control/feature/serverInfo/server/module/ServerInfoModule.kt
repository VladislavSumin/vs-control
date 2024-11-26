package ru.vs.control.feature.serverInfo.server.module

import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import ru.vs.control.feature.serverInfo.ServerInfo
import ru.vs.core.ktor.server.KtorServerModule

class ServerInfoModule : KtorServerModule {
    override fun Application.module() {
        routing {
            get("/api/info") {
                // TODO добавить провайдер версии
                call.respond(ServerInfo("0.0.1"))
            }
        }
    }
}
