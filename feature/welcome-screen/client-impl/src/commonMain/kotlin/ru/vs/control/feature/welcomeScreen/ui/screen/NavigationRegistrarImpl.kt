package ru.vs.control.feature.welcomeScreen.ui.screen

import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenFactory
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.navigation.NavigationRegistrar
import ru.vs.navigation.NavigationRegistry
import ru.vs.navigation.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(WelcomeScreenParams::class), WelcomeScreenFactory())
        registerDefaultScreenParams(WelcomeScreenParams)
    }
}
