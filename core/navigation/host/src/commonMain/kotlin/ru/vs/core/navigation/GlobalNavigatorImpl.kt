package ru.vs.core.navigation

internal class GlobalNavigatorImpl(
    override val navigationGraph: NavigationGraph,
) : GlobalNavigator {
    override fun registerHostNavigator(hostNavigator: HostNavigator) {
        // TODO
    }

    override fun unregisterHostNavigator(hostNavigator: HostNavigator) {
        // TODO
    }
}
