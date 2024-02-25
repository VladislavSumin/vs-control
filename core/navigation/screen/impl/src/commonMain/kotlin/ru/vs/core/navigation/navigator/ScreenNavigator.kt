package ru.vs.core.navigation.navigator

import ru.vs.core.navigation.NavigationGraph
import ru.vs.core.navigation.NavigationHost

class ScreenNavigator(
    internal val navigationGraph: NavigationGraph,
) {
    internal fun registerHostNavigator(navigationHost: NavigationHost, hostNavigator: HostNavigator) {
    }

    internal fun unregisterHostNavigator(navigationHost: NavigationHost) {
    }
}
