package ru.vs.control.feature.mainScreen.ui.screen

import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenFactory
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenParams
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentNavigationHost
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.asKey

internal class NavigationRegistrarImpl(
    private val mainScreenFactory: MainScreenFactory,
) : NavigationRegistrar {
    override val nameForLogs: String = "ru.vs.control.feature.mainScreen.ui.screen.NavigationRegistrarImpl"

    override fun NavigationRegistry.register() {
        registerScreen(
            key = MainScreenParams.asKey(),
            factory = mainScreenFactory,
            paramsSerializer = MainScreenParams.serializer(),
            nameForLogs = "MainScreenParams",
            defaultParams = MainScreenParams,
            opensIn = setOf(RootContentNavigationHost),
            description = "Главный экран приложения. С табиками и тд.",
        )
    }
}
