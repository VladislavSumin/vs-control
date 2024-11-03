package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@GenerateFactory
internal class WelcomeScreenViewModel : NavigationViewModel() {
    fun onClickContinue() = open(RootContentScreenParams)
}
