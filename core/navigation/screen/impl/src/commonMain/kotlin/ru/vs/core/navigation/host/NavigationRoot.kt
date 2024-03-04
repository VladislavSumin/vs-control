package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.graph.NavigationGraph
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory

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
    val node = navigationGraph.tree.root
    val params = node.screenRegistration.defaultParams ?: error("Root screen must have default params")
    val rootScreenFactory = node.screenRegistration.factory as ScreenFactory<ScreenParams, out Screen>?
    check(rootScreenFactory != null) { "Factory for $params not found" }
    val rootScreenContext = childRootScreenContext(navigationGraph, node, params, key)
    return rootScreenFactory.create(rootScreenContext, params)
}
