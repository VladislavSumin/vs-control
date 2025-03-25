package ru.vs.core.navigation.tree

import ru.vladislavsumin.core.navigation.NavigationHost
import ru.vladislavsumin.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

internal data class ScreenInfo(
    val screenKey: ScreenKey<*>,
    val factory: ScreenFactory<*, *>,
    val defaultParams: ScreenParams?,
    val description: String?,
    val hostInParent: NavigationHost?,
    val navigationHosts: Set<NavigationHost>,
)
