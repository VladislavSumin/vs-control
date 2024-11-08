package ru.vs.core.navigation.registration

import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import org.kodein.di.inBindSet
import org.kodein.di.provider

/**
 * Синтаксический сахар для регистрации навигации.
 * @see NavigationRegistrar
 */
inline fun <reified T : NavigationRegistrar> DI.Builder.bindNavigation(crossinline block: DirectDIAware.() -> T) {
    inBindSet<NavigationRegistrar> {
        add { provider { block() } }
    }
}
