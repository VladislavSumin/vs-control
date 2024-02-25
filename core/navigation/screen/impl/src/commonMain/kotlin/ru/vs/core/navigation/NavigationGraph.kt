package ru.vs.core.navigation

import ru.vs.core.navigation.registration.NavigationRegistrar
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
    private val repository: NavigationRepository = NavigationRepositoryImpl(registrars)
    private val rootScreen = findRootScreen(repository)

    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     */
    internal fun <P : ScreenParams> findFactory(screenKey: ScreenKey<P>): ScreenFactory<P, out Screen>? {
        // Успешность каста гарантирована контрактом репозитория.
        @Suppress("UNCHECKED_CAST")
        return repository.screenFactories[screenKey] as ScreenFactory<P, out Screen>?
    }

    /**
     * Возвращает параметры корневого экрана
     */
    internal fun getRootScreenParams(): ScreenParams {
        return repository.defaultScreenParams[rootScreen] ?: error("Root screen must have default params")
    }

    /**
     * Ищет root screen, этим экраном является такой экран который невозможно открыть из другой точки графа.
     */
    private fun findRootScreen(repository: NavigationRepository): ScreenKey<out ScreenParams> {
        // TODO супер временное решение, пока просто берем первый экран из репозитория
        return repository.screenFactories.keys.first()
    }
}
