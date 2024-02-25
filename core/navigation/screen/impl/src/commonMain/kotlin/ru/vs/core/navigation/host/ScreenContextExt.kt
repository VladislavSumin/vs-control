package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.vs.core.navigation.NavigationGraph
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.ScreenNavigator
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenPath

internal fun ComponentContext.childRootScreenContext(
    navigationGraph: NavigationGraph,
    key: String,
    lifecycle: Lifecycle? = null,
): ScreenContext {
    return DefaultScreenContext(
        ScreenNavigator(navigationGraph, ScreenPath(listOf())),
        childContext(key, lifecycle),
    )
}

internal fun ComponentContext.wrapWithScreenContext(
    parentNavigator: ScreenNavigator,
    screenParams: ScreenParams,
): ScreenContext {
    return DefaultScreenContext(
        ScreenNavigator(
            parentNavigator.navigationGraph,
            parentNavigator.screenPath + screenParams,
        ),
        this,
    )
}

private class DefaultScreenContext(
    override val screenNavigator: ScreenNavigator,
    context: ComponentContext
) : ScreenContext, ComponentContext by context
