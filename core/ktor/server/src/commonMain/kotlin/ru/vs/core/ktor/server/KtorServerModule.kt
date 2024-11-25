package ru.vs.core.ktor.server

import io.ktor.server.application.Application
import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import org.kodein.di.inBindSet
import org.kodein.di.provider

/**
 * Общее объявление серверных модулей.
 * @see bindKtorServerModule
 */
interface KtorServerModule {
    fun Application.module()
}

/**
 * Синтаксический сахар для регистрации серверных модулей.
 * @see KtorServerModule
 */
inline fun <reified T : KtorServerModule> DI.Builder.bindKtorServerModule(crossinline block: DirectDIAware.() -> T) {
    inBindSet<KtorServerModule> {
        add { provider { block() } }
    }
}
