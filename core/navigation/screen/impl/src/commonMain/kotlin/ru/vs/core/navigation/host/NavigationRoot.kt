package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.graph.NavigationGraph
import ru.vs.core.navigation.screen.ScreenKey
import kotlin.reflect.KClass

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
    val rootScreenParams: ScreenParams = navigationGraph.getRootScreenParams()

    @Suppress("UNCHECKED_CAST") // Безопасность каста обеспечивается контрактом графа навигации.
    val rootScreenKey: KClass<ScreenParams> = rootScreenParams::class as KClass<ScreenParams>

    val rootScreenFactory = navigationGraph.findFactory(ScreenKey(rootScreenKey))
    check(rootScreenFactory != null) { "Factory for $rootScreenParams not found" }
    val rootScreenContext = childRootScreenContext(navigationGraph, key)
    return rootScreenFactory.create(rootScreenContext, rootScreenParams)
}
