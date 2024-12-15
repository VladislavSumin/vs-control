package ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen

import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractor
import ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.ScreenParams

@GenerateFactory
internal class RootNavigationScreenViewModel(
    private val welcomeInteractor: WelcomeInteractor,
) : ViewModel() {
    /**
     * Возвращает дефолтные параметры для экрана.
     */
    suspend fun getInitialConfiguration(): ScreenParams {
        return if (welcomeInteractor.isNeedToShowWelcomeScreen()) WelcomeScreenParams else RootContentScreenParams
    }
}
