package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.tree.NavigationTree

/**
 * Корневая точка входа в навигацию.
 *
 * @param navigationTree дерево навигации.
 * @param key уникальный в пределах компонента ключ дочернего компонента.
 */
fun ComponentContext.childNavigationRoot(
    navigationTree: NavigationTree,
    key: String = "navigation-root",
): ComposeComponent {
    val node = navigationTree.root
    val params = node.screenRegistration.defaultParams ?: error("Root screen must have default params")
    val rootScreenFactory = node.screenRegistration.factory as ScreenFactory<ScreenParams, out Screen>?
    check(rootScreenFactory != null) { "Factory for $params not found" }
    val rootScreenContext = childRootScreenContext(navigationTree, node, params, key)
    return rootScreenFactory.create(rootScreenContext, params)
}
