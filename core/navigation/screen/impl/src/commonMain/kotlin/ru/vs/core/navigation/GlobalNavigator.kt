package ru.vs.core.navigation

/**
 * Внутренний навигатор обеспечивающий связность хостов навигации.
 * Не используйте его напрямую. Все нужное для навигации api можно найти в [ScreenContext].
 */
@NavigationInternalApi
interface GlobalNavigator {
    val navigationGraph: NavigationGraph

    fun registerHostNavigator(hostNavigator: HostNavigator)
    fun unregisterHostNavigator(hostNavigator: HostNavigator)
}
