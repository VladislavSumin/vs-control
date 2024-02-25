package ru.vs.core.navigation

/**
 * Внутренний навигатор обеспечивающий связность хостов навигации.
 * Не используйте его напрямую. Все нужное для навигации api можно найти в [ScreenContext].
 */
internal interface GlobalNavigator {
    // TODO
    fun ScreenContext.registerHostNavigator(hostNavigator: HostNavigator)
}
