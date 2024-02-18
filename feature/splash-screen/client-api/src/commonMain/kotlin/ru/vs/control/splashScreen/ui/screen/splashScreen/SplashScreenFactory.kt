package ru.vs.control.splashScreen.ui.screen.splashScreen

import com.arkivanov.decompose.ComponentContext

interface SplashScreenFactory {
    fun create(context: ComponentContext): SplashScreenComponent
}
