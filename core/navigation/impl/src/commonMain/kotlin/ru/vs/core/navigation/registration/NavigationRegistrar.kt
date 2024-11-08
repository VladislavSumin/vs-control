package ru.vs.core.navigation.registration

/**
 * Этот интерфейс необходимо реализовать в вашем модуле и вернуть его в графе навигации через [bindNavigation]:
 * ```kotlin
 * bindNavigation {
 *     NavigationRegistrarImpl()
 * }
 * ```
 * После чего можно зарегистрировать компоненты навигации получив [NavigationRegistry] в методе [register].
 */
interface NavigationRegistrar {
    /**
     * Название класса для логов. В js мы не можем использовать рефлексию class.qualifiedName поэтому вынуждены
     * пока использовать этот костыль.
     */
    val nameForLogs: String

    /**
     * Регистрирует фабрики, хосты навигации и экраны в хостах.
     */
    fun NavigationRegistry.register()
}
