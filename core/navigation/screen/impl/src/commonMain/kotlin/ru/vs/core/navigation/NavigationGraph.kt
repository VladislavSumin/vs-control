package ru.vs.core.navigation

/**
 * Предоставляет доступ к основному графу навигации.
 * Прямое использование графа не предусмотрено фреймворком, необходимо только передать его в корневой хост навигации.
 */
interface NavigationGraph {
    /**
     * Ищет фабрику для экрана соответствующего ключу [screenKey].
     * Не используйте этот метод напрямую.
     */
    @NavigationInternalApi
    fun <P : ScreenParams> findFactory(screenKey: ScreenKey<P>): ScreenFactory<P, out Screen>?

    /**
     * Возвращает параметры корневого экрана
     * Не используйте этот метод напрямую.
     */
    @NavigationInternalApi
    fun getRootScreenParams(): ScreenParams
}
