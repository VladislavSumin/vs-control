package ru.vs.control.feature.auth.client.domain

import io.ktor.http.Url
import ru.vs.control.feature.auth.AuthResponse
import ru.vs.control.feature.auth.client.api.ServerAuthApi
import ru.vs.core.ktor.client.SafeResponse

internal class ServerAuthInteractorImpl(
    private val serverAuthApi: ServerAuthApi,
) : ServerAuthInteractor {
    override suspend fun login(url: Url, login: String, password: String): ServerAuthResult {
        val response = serverAuthApi.login(url, login, password)
        return when (response) {
            is SafeResponse.UnknownError<*> -> ServerAuthResult.NetworkError(response.error)
            is SafeResponse.Success<AuthResponse> -> ServerAuthResult.Success(response.value.accessToken)
        }
    }
}
