package ru.vs.navigation

interface NavigationRepository {
    val screenFactories: Map<ScreenKey<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>
    val defaultScreenParams: Map<ScreenKey<out ScreenParams>, ScreenParams>
    val navigationHosts: Map<ScreenKey<out ScreenParams>, NavigationHost>
    val endpoints: Map<NavigationHost, Set<ScreenKey<ScreenParams>>>
}
