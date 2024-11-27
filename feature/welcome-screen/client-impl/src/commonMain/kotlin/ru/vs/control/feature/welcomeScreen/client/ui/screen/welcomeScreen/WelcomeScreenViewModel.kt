package ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorInternal
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@GenerateFactory
internal class WelcomeScreenViewModel(
    private val welcomeInteractorInternal: WelcomeInteractorInternal,
) : NavigationViewModel() {
    fun onClickContinue() = launch {
        welcomeInteractorInternal.passWelcomeScreen()
        open(RootContentScreenParams)
    }
}
