package ru.vs.core.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

/**
 * Фабрика для создания сетевых клиентов.
 */
interface HttpClientFactory {
    /**
     * Создает [HttpClient] по умолчанию. Инстанс такого клиента уже есть в DI и создавать отдельные сущности без
     * веских причин не стоит.
     */
    fun createDefault(): HttpClient
}

internal class HttpClientFactoryImpl : HttpClientFactory {
    override fun createDefault(): HttpClient {
        return HttpClient(CIO)
    }
}
