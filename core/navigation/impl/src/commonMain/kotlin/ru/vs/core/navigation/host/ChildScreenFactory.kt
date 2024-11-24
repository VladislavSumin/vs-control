package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.asErasedKey
import ru.vs.core.navigation.screen.wrapWithScreenContext
import ru.vs.core.navigation.ui.debug.navigationHost.wrapWithNavigationHostDebugOverlay

/**
 * Стандартная фабрика дочерних экранов для использования в compose навигации.
 *
 * @param hostType тип хоста навигации для которого создается экран.
 * @param screenParams параметры экрана
 * @param context контекст экрана
 */
internal fun ScreenContext.childScreenFactory(
    hostType: HostType,
    screenParams: ScreenParams,
    context: ComponentContext,
): ComposeComponent {
    val screenContext = context.wrapWithScreenContext(navigator, screenParams)
    val screenFactory = navigator.getChildScreenFactory(screenParams.asErasedKey())
    val screen = screenFactory.create(screenContext, screenParams)
    screenContext.navigator.screen = screen
    return screen.wrapWithNavigationHostDebugOverlay(hostType)
}
