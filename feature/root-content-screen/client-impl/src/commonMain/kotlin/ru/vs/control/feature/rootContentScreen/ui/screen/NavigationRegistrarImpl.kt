package ru.vs.control.feature.rootContentScreen.ui.screen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenFactory
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.navigation.NavigationRegistrar
import ru.vs.core.navigation.NavigationRegistry
import ru.vs.core.navigation.ScreenKey

class NavigationRegistrarImpl : NavigationRegistrar {
    override fun NavigationRegistry.register() {
        registerScreenFactory(ScreenKey(RootContentScreenParams::class), RootContentScreenFactory())
    }
}
