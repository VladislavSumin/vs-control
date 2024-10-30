package ru.vs.control.feature.mainScreen.ui.screen

import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.ScreenKey

internal class NavigationRegistrarImpl(
    private val mainScreenFactory: MainScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.mainScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = ScreenKey(MainScreenParams::class),
            factory = mainScreenFactory,
            paramsSerializer = MainScreenParams.serializer(),
            nameForLogs = "MainScreenParams",
            defaultParams = MainScreenParams,
            description = "Главный экран приложения. С табиками и тд.",
        )
        registerScreenNavigation(RootContentNavigationHost, ScreenKey(MainScreenParams::class))
    }
}
