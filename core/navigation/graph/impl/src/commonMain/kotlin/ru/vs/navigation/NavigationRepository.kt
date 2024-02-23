package ru.vs.navigation

import kotlin.reflect.KClass

interface NavigationRepository {
    val screenFactories: Map<KClass<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>
    val navigationHosts: Map<KClass<out ScreenParams>, NavigationHost>
    val endpoints: Map<NavigationHost, MutableSet<KClass<ScreenParams>>>
}
