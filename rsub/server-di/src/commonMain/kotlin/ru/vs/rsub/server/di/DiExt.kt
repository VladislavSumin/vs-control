package ru.vs.rsub.server.di

import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import org.kodein.di.inBindSet
import org.kodein.di.provider
import ru.vs.rsub.RSubServerInterface

/**
 * Синтаксический сахар для регистрации [RSubServerInterface].
 * @see RSubServerInterface
 */
inline fun <reified T : RSubServerInterface> DI.Builder.bindRsubServerInterface(
    crossinline block: DirectDIAware.() -> T,
) {
    inBindSet<RSubServerInterface> {
        add { provider { block() } }
    }
}
