package ru.vs.control.feature.auth.client.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import ru.vs.control.feature.auth.AuthRequest
import ru.vs.control.feature.auth.AuthResponse
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.ktor.client.handleConnectionErrors

internal interface ServerAuthApi {
    suspend fun login(url: Url, login: String, password: String): SafeResponse<AuthResponse>
}

internal class ServerAuthApiImpl(
    private val httpClient: HttpClient,
) : ServerAuthApi {
    override suspend fun login(
        url: Url,
        login: String,
        password: String,
    ): SafeResponse<AuthResponse> {
        val url = Url("$url/api/login")
        return handleConnectionErrors {
            httpClient.post(url) {
                contentType(ContentType.Application.ProtoBuf)
                setBody(AuthRequest(login, password))
            }.body()
        }
    }
}
