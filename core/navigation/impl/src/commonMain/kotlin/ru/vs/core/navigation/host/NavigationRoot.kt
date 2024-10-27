package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.GlobalNavigator
import ru.vs.core.navigation.navigator.ScreenNavigator
import ru.vs.core.navigation.screen.DefaultScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenPath
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
    val rootScreenFactory = node.screenRegistration.factory as ScreenFactory<ScreenParams, *>?
    check(rootScreenFactory != null) { "Factory for $params not found" }

    // Создаем рутовый навигатор.
    val globalNavigator = GlobalNavigator(navigationTree)

    // Создаем дочерний контекст который будет являться контекстом для корневого экрана графа навигации.
    // Lifecycle полученного компонента будет совпадать с родителем
    val childContext = childContext(key, lifecycle = null)

    val rootScreenContext = DefaultScreenContext(
        ScreenNavigator(
            globalNavigator = globalNavigator,
            screenPath = ScreenPath(params),
            node = node,
        ),
        childContext,
    )

    return rootScreenFactory.create(rootScreenContext, params)
}
