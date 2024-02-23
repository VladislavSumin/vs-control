package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.vs.core.decompose.ComposeComponent
import ru.vs.navigation.NavigationGraph
import ru.vs.navigation.ScreenContext

/**
 * Корневая точка входа в навигацию.
 *
 * @param navigationGraph граф навигации.
 * @param key уникальный в пределах компонента ключ дочернего компонента.
 */
fun ComponentContext.childNavigationRoot(
    navigationGraph: NavigationGraph,
    key: String = "navigation-root",
): ComposeComponent {
    val rootScreenParams = navigationGraph.getRootScreenParams()
    val rootScreenFactory = navigationGraph.findFactory(rootScreenParams::class)
    check(rootScreenFactory != null) { "Factory for $rootScreenParams not found" }
    val rootScreenContext = childScreenContext(key)
    return rootScreenFactory.create(rootScreenContext, rootScreenParams)
}

/**
 * Создает рутовый контекст навигации.
 */
private fun ComponentContext.childScreenContext(
    key: String,
    lifecycle: Lifecycle? = null,
): ScreenContext = DefaultScreenContext(childContext(key, lifecycle))

private class DefaultScreenContext(context: ComponentContext) : ScreenContext, ComponentContext by context
