package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.ScreenNavigator
import ru.vs.core.navigation.screen.DefaultScreenContext
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenKey

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
