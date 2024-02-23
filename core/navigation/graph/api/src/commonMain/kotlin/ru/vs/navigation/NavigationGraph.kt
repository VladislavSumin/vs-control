package ru.vs.navigation

import kotlin.reflect.KClass

/**
 * Предоставляет доступ к основному графу навигации.
 */
interface NavigationGraph {
    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     */
    fun <P : ScreenParams> findFactory(screenKey: KClass<P>): ScreenFactory<P, out Screen>?

    /**
     * Возвращает параметры корневого экрана
     */
    fun getRootScreenParams(): ScreenParams
}
