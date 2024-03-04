package ru.vs.core.navigation.graph

import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.repository.NavigationRepositoryImpl
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

/**
 * Предоставляет доступ к основному графу навигации.
 * Прямое использование графа не предусмотрено фреймворком, необходимо только передать его в корневой хост навигации.
 */
class NavigationGraph internal constructor(
    registrars: Set<NavigationRegistrar>,
) {
    internal val repository: NavigationRepository = NavigationRepositoryImpl(registrars)
    internal val tree = NavigationTree(repository)

    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     */
    internal fun <P : ScreenParams> findFactory(screenKey: ScreenKey<P>): ScreenFactory<P, out Screen>? {
        // Успешность каста гарантирована контрактом репозитория.
        @Suppress("UNCHECKED_CAST")
        return repository.screens[screenKey]?.factory as ScreenFactory<P, out Screen>?
    }

    /**
     * Возвращает параметры корневого экрана
     */
    internal fun getRootScreenParams(): ScreenParams {
        return tree.rootScreenRegistration.defaultParams ?: error("Root screen must have default params")
    }
}
