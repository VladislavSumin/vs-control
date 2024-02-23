package ru.vs.navigation

internal class NavigationGraphImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationGraph {
    // TODO пока не используется, на будущее.
    @Suppress("UnusedPrivateProperty")
    private val registry = NavigationRegistryImpl(registrars)
}
