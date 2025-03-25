package ru.vs.core.navigation.screen

import com.arkivanov.decompose.ComponentContext
import ru.vladislavsumin.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.ScreenNavigator

/**
 * Базовый контекст навигации, обычно используется только внутри библиотеки для развязки делегирования
 * [BaseScreenContext] и [ComponentContext] при реализации интерфейса [ScreenContext].
 */
interface BaseScreenContext {
    /**
     * Предоставляет доступ к навигации с учетом контекста этого экрана.
     *
     * Доступ к контексту означает что поиск ближайшего экрана будет происходить не от корня графа, а от текущего этого
     * экрана.
     */
    val navigator: ScreenNavigator
}

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : BaseScreenContext, ComponentContext

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
    val childNode = parentNavigator.node.children.find { it.value.screenKey == screenKey }
    check(childNode != null) {
        "Screen ${screenParams.asKey()} is not a child for screen ${parentNavigator.screenParams.asKey()}"
    }

    val initialPath = parentNavigator.initialPath?.let { path ->
        val newPath = path.drop(1)
        if (newPath.isNotEmpty()) {
            ScreenPath(newPath)
        } else {
            null
        }
    }

    return DefaultScreenContext(
        ScreenNavigator(
            globalNavigator = parentNavigator.globalNavigator,
            parentNavigator = parentNavigator,
            screenPath = parentNavigator.screenPath + screenParams,
            node = childNode,
            serializer = parentNavigator.serializer,
            lifecycle = lifecycle,
            initialPath = initialPath,
        ),
        this,
    )
}
