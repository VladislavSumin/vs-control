package ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen

import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.control.feature.welcomeScreen.client.domain.WelcomeInteractorInternal

@GenerateFactory
internal class WelcomeScreenViewModel(
    private val welcomeInteractorInternal: WelcomeInteractorInternal,
) : NavigationViewModel() {
    fun onClickContinue() = launch {
        welcomeInteractorInternal.passWelcomeScreen()
        open(RootContentScreenParams)
    }
}
