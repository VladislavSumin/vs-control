package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

/**
 * Фабрика для создания Splash экрана.
 * Splash экран, это экран инициализации приложения, показывается перед всеми другими экранами на
 * время необходимое для полной инициализации приложения.
 */
interface SplashScreenFactory {
    fun create(context: ComponentContext): ComposeComponent
}
