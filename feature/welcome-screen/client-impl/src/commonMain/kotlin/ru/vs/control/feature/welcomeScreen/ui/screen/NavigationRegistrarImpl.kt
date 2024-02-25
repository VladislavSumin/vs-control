package ru.vs.control.feature.welcomeScreen.ui.screen

import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.navigation.NavigationRegistrar
import ru.vs.core.navigation.NavigationRegistry
import ru.vs.core.navigation.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(WelcomeScreenParams::class), WelcomeScreenFactory())
        registerDefaultScreenParams(WelcomeScreenParams)
    }
}
