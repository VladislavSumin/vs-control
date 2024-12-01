package ru.vs.control.feature.auth.server.module

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ru.vs.control.feature.auth.AuthRequest
import ru.vs.control.feature.auth.AuthResponse
import ru.vs.core.ktor.server.KtorServerModule

internal class ServerAuthModule : KtorServerModule {
    override fun Application.module() {
        routing {
            post("/api/login") {
                val request = call.receive<AuthRequest>()
                if (request.login == "admin" && request.password == "admin") {
                    call.respond(AuthResponse("accessToken"))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Incorrect login or password")
                }
            }
        }
    }
}
