package ru.vs.navigation

import kotlin.reflect.KClass

interface NavigationRepository {
    val screenFactories: Map<KClass<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>
    val defaultScreenParams: Map<KClass<out ScreenParams>, ScreenParams>
    val navigationHosts: Map<KClass<out ScreenParams>, NavigationHost>
    val endpoints: Map<NavigationHost, Set<KClass<ScreenParams>>>
}
