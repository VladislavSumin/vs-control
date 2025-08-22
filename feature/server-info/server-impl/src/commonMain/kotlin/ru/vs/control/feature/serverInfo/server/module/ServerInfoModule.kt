package ru.vs.control.feature.serverInfo.server.module

import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.first
import ru.vs.control.feature.serverInfo.server.domain.ServerInfoInteractor
import ru.vs.core.ktor.server.KtorServerModule

internal class ServerInfoModule(
    private val serverInfoInteractor: ServerInfoInteractor,
) : KtorServerModule {
    override fun Application.module() {
        routing {
            get("/api/info") {
                call.respond(serverInfoInteractor.observeServerInfo().first())
            }
        }
    }
}
