package ru.vs.core.navigation.screen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.ScreenParams
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

/**
 * **Оборачивает** [ComponentContext] добавляя в него [ScreenNavigator]. Создание дочернего компонента не происходит.
 *
 * @param parentNavigator навигатор родительского экрана.
 * @param screenParams параметры с которыми создается этот экран.
 */
internal fun ComponentContext.wrapWithScreenContext(
    parentNavigator: ScreenNavigator,
    screenParams: ScreenParams,
): ScreenContext {
    val screenKey = ScreenKey(screenParams::class)
    return DefaultScreenContext(
        ScreenNavigator(
            parentNavigator.globalNavigator,
            parentNavigator.screenPath + screenParams,
            parentNavigator.node.children.find { it.value.screenKey == screenKey }!!,
            lifecycle,
        ),
        this,
    )
}
