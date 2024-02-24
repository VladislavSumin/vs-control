package ru.vs.navigation

import com.arkivanov.decompose.ComponentContext

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : ComponentContext {
    // TODO подумать как бы сделать это internal
    val navigationGraph: NavigationGraph
}
