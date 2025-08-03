package ru.vs.core.ktor.client

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i

fun Modules.coreKtorClient() = DI.Module("core-ktor-client") {
    bindSingleton<HttpClientFactory> { HttpClientFactoryImpl() }
    bindSingleton<HttpClient> { i<HttpClientFactory>().createDefault() }
}
