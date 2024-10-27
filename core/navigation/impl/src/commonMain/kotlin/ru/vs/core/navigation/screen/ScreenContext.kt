package ru.vs.core.navigation.screen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.navigator.ScreenNavigator

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : ComponentContext {
    /**
     * Предоставляет доступ к навигации с учетом контекста этого экрана.
     *
     * Доступ к контексту означает что поиск ближайшего экрана будет происходить не от корня графа, а от текущего этого
     * экрана.
     */
    val navigator: ScreenNavigator
}

internal class DefaultScreenContext(
    override val navigator: ScreenNavigator,
    context: ComponentContext,
) : ScreenContext, ComponentContext by context
