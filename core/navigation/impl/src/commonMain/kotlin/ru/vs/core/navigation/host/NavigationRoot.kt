package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.Navigation
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.GlobalNavigator
import ru.vs.core.navigation.navigator.ScreenNavigator
import ru.vs.core.navigation.screen.DefaultScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenPath

/**
 * Корневая точка входа в навигацию.
 *
 * @param navigationTree дерево навигации.
 * @param key уникальный в пределах компонента ключ дочернего компонента.
 */
fun ComponentContext.childNavigationRoot(
    navigation: Navigation,
    key: String = "navigation-root",
): ComposeComponent {
    val node = navigation.navigationTree.root
    val params = node.screenInfo.defaultParams ?: error("Root screen must have default params")
    val rootScreenFactory = node.screenInfo.factory as ScreenFactory<ScreenParams, *>?
    check(rootScreenFactory != null) { "Factory for $params not found" }

    // Создаем рутовый навигатор.
    val globalNavigator = GlobalNavigator(navigation.navigationTree)

    // Создаем дочерний контекст который будет являться контекстом для корневого экрана графа навигации.
    // Lifecycle полученного компонента будет совпадать с родителем
    val childContext = childContext(key, lifecycle = null)

    val rootScreenContext = DefaultScreenContext(
        ScreenNavigator(
            globalNavigator = globalNavigator,
            screenPath = ScreenPath(params),
            node = node,
            lifecycle = childContext.lifecycle,
        ),
        childContext,
    )

    return rootScreenFactory.create(rootScreenContext, params)
}
