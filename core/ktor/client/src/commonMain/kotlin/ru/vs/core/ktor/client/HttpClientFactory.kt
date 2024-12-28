package ru.vs.core.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.protobuf.protobuf
import kotlinx.serialization.ExperimentalSerializationApi

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
    @OptIn(ExperimentalSerializationApi::class)
    override fun createDefault(): HttpClient {
        return HttpClient(CIO) {
            // TODO переписать на конфигурацию снаружи модуля.
            install(WebSockets)
            // TODO переписать на конфигурацию снаружи модуля.
            install(ContentNegotiation) {
                // TODO переписать на конфигурацию снаружи модуля.
                protobuf()
            }
        }
    }
}
