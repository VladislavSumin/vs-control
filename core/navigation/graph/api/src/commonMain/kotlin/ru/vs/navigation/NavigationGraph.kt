package ru.vs.navigation

/**
 * Предоставляет доступ к основному графу навигации.
 */
interface NavigationGraph {
    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     */
    fun <P : ScreenParams> findFactory(screenKey: ScreenKey<P>): ScreenFactory<P, out Screen>?

    /**
     * Возвращает параметры корневого экрана
     */
    fun getRootScreenParams(): ScreenParams
}
