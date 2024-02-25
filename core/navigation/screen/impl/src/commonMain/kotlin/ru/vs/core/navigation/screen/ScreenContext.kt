package ru.vs.core.navigation.screen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.navigator.ScreenNavigator

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : ComponentContext {
    // TODO доку.
    val screenNavigator: ScreenNavigator
}
