package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenPath

/**
 * @param screenPath путь до экрана соответствующего данному навигатору.
 */
context(ComponentContext)
class ScreenNavigator internal constructor(
    internal val globalNavigator: GlobalNavigator,
    internal val screenPath: ScreenPath
) {
    private val navigationHosts = mutableMapOf<NavigationHost, HostNavigator>()

    init {
        globalNavigator.registerScreenNavigator(this)
    }

    /**
     * Регистрирует [HostNavigator] для [NavigationHost] навигации. Учитывает lifecycle [ScreenContext]
     */
    internal fun registerHostNavigator(navigationHost: NavigationHost, hostNavigator: HostNavigator) {
        val oldHost = navigationHosts.put(navigationHost, hostNavigator)
        check(oldHost == null) { "Navigation host $navigationHost already registered" }
        lifecycle.doOnDestroy {
            val host = navigationHosts.remove(navigationHost)
            check(host != null) { "Navigation host $navigationHost not found" }
        }
    }
}
