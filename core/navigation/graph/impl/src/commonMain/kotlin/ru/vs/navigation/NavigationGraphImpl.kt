package ru.vs.navigation

import kotlin.reflect.KClass

internal class NavigationGraphImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationGraph {
    // TODO пока не используется, на будущее.
    @Suppress("UnusedPrivateProperty")
    private val repository: NavigationRepository = NavigationRepositoryImpl(registrars)

    override fun findFactory(screenKey: KClass<out ScreenParams>): ScreenFactory<ScreenParams, Screen>? {
        TODO("Not yet implemented")
    }

    override fun getRootScreenParams(): ScreenParams {
        TODO("Not yet implemented")
    }
}
