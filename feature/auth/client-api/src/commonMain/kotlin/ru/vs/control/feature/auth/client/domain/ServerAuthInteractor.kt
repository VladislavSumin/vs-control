package ru.vs.control.feature.auth.client.domain

import io.ktor.http.Url

interface ServerAuthInteractor {
    suspend fun login(url: Url, login: String, password: String): ServerAuthResult
}
