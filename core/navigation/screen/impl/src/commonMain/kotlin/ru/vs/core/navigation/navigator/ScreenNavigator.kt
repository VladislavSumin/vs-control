package ru.vs.core.navigation.navigator

import ru.vs.core.navigation.NavigationGraph
import ru.vs.core.navigation.NavigationHost

class ScreenNavigator internal constructor(
    internal val navigationGraph: NavigationGraph,
) {
    private val navigationHosts = mutableMapOf<NavigationHost, HostNavigator>()

    internal fun registerHostNavigator(navigationHost: NavigationHost, hostNavigator: HostNavigator) {
        val oldHost = navigationHosts.put(navigationHost, hostNavigator)
        check(oldHost == null) { "Navigation host $navigationHost already registered" }
    }

    internal fun unregisterHostNavigator(navigationHost: NavigationHost) {
        val host = navigationHosts.remove(navigationHost)
        check(host != null) { "Navigation host $navigationHost not found" }
    }
}
