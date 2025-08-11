package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.core.decompose.context.VsComponentContext

/**
 * Фабрика для создания Splash экрана.
 * Splash экран, это экран инициализации приложения, показывается перед всеми другими экранами на
 * время необходимое для полной инициализации приложения.
 */
interface SplashScreenFactory {
    fun create(context: VsComponentContext): ComposeComponent
}
