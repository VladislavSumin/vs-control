package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.GlobalNavigator
import ru.vs.core.navigation.navigator.ScreenNavigator
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.tree.NavigationTree

// TODO удалить
internal fun ComponentContext.childRootScreenContext(
    navigationTree: NavigationTree,
    node: NavigationTree.Node,
    params: ScreenParams,
    key: String,
    lifecycle: Lifecycle? = null,
): ScreenContext {
    val childContext = childContext(key, lifecycle)
    with(childContext) {
        return DefaultScreenContext(
            ScreenNavigator(
                GlobalNavigator(navigationTree),
                ScreenPath(listOf(params)),
                node,
            ),
            childContext,
        )
    }
}

internal fun ComponentContext.wrapWithScreenContext(
    parentNavigator: ScreenNavigator,
    screenParams: ScreenParams,
): ScreenContext {
    val screenKey = ScreenKey(screenParams::class)
    return DefaultScreenContext(
        ScreenNavigator(
            parentNavigator.globalNavigator,
            parentNavigator.screenPath + screenParams,
            parentNavigator.node.children[screenKey]!!,
        ),
        this,
    )
}

private class DefaultScreenContext(
    override val navigator: ScreenNavigator,
    context: ComponentContext,
) : ScreenContext, ComponentContext by context
