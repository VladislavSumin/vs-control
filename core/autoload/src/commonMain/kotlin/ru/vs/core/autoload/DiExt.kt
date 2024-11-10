package ru.vs.core.autoload

import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import org.kodein.di.inBindSet
import org.kodein.di.provider

/**
 * Синтаксический сахар для регистрации [AutoloadService].
 * @see AutoloadService
 */
inline fun <reified T : AutoloadService> DI.Builder.bindAutoload(crossinline block: DirectDIAware.() -> T) {
    inBindSet<AutoloadService> {
        add { provider { block() } }
    }
}
