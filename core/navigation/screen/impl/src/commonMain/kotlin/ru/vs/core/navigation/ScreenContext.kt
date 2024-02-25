package ru.vs.core.navigation

import com.arkivanov.decompose.ComponentContext

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : ComponentContext {
    @NavigationInternalApi
    val navigationGraph: NavigationGraph
}
