package ru.vs.core.decompose.context

import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import ru.vladislavsumin.core.navigation.registration.bindGenericNavigation

inline fun <reified T : VsNavigationRegistrar> DI.Builder.bindNavigation(
    crossinline block: DirectDIAware.() -> T,
) = bindGenericNavigation(block)
