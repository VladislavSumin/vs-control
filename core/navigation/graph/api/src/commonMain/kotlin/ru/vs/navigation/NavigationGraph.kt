package ru.vs.navigation

import kotlin.reflect.KClass

/**
 * Предоставляет доступ к основному графу навигации.
 */
interface NavigationGraph {
    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     */
    fun findFactory(screenKey: KClass<out ScreenParams>): ScreenFactory<ScreenParams, Screen>?

    /**
     * Возвращает параметры корневого экрана
     */
    fun getRootScreenParams(): ScreenParams
}
