package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.vs.core.navigation.NavigationGraph
import ru.vs.core.navigation.screen.ScreenPath

/**
 * Глобальный навигатор.
 */
internal class GlobalNavigator(
    internal val navigationGraph: NavigationGraph,
) {
    private val screenNavigators = mutableMapOf<ScreenPath, ScreenNavigator>()

    /**
     * Регистрирует [screenNavigator] с учетом жизненного цикла [ComponentContext]
     */
    context(ComponentContext)
    fun registerScreenNavigator(screenNavigator: ScreenNavigator) {
        val oldScreenNavigator = screenNavigators.put(screenNavigator.screenPath, screenNavigator)
        check(oldScreenNavigator == null) {
            "Screen navigator for ${screenNavigator.screenPath} already registered"
        }
        lifecycle.doOnDestroy {
            val navigator = screenNavigators.remove(screenNavigator.screenPath)
            check(navigator != null) { "Screen navigator fro ${screenNavigator.screenPath} not found" }
        }
    }
}
