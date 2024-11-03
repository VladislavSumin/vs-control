package ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.control.feature.welcomeScreen.domain.WelcomeInteractor
import ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen.WelcomeScreenParams
import ru.vs.core.decompose.ViewModel
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.ScreenParams

@GenerateFactory
internal class RootNavigationScreenViewModel(
    private val welcomeInteractor: WelcomeInteractor,
) : ViewModel() {
    /**
     * Возвращает дефолтные параметры для экрана.
     */
    fun getInitialConfiguration(): ScreenParams {
        return if (welcomeInteractor.isNeedToShowWelcomeScreen) WelcomeScreenParams else RootContentScreenParams
    }
}
