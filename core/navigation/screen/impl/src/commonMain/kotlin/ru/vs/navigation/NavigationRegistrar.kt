package ru.vs.navigation

/**
 * Этот интерфейс необходимо реализовать в вашем модуле и вернуть его в графе навигации в виде:
 * ```kotlin
 * // TODO добавить пример DI.
 * ```
 * После чего можно зарегистрировать компоненты навигации получив [NavigationRegistry] в методе [register].
 */
interface NavigationRegistrar {
    /**
     * Регистрирует фабрики, хосты навигации и экраны в хостах.
     */
    fun NavigationRegistry.register()
}
