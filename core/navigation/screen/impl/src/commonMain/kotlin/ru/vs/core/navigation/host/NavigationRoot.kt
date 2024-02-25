package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.NavigationGraph
import ru.vs.core.navigation.ScreenContext
import ru.vs.core.navigation.ScreenKey
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.ScreenNavigator
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
    val rootScreenContext = childScreenContext(navigationGraph, key)
    return rootScreenFactory.create(rootScreenContext, rootScreenParams)
}

/**
 * Создает рутовый контекст навигации.
 */
private fun ComponentContext.childScreenContext(
    navigationGraph: NavigationGraph,
    key: String,
    lifecycle: Lifecycle? = null,
): ScreenContext = DefaultScreenContext(
    ScreenNavigator(navigationGraph),
    childContext(key, lifecycle),
)

private class DefaultScreenContext(
    override val screenNavigator: ScreenNavigator,
    context: ComponentContext
) : ScreenContext, ComponentContext by context
