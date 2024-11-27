package ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen

import kotlinx.serialization.Serializable
import ru.vs.core.navigation.ScreenParams

/**
 * Корневой экран навигации. Объявлен не в api модуле так как навигация на конкретно этот экран пока не требуется.
 */
@Serializable
internal data object RootNavigationScreenParams : ScreenParams
