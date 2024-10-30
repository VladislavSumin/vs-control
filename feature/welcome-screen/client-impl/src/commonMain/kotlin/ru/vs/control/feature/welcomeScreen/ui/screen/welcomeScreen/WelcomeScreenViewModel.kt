package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.navigation.viewModel.NavigationViewModel

internal class WelcomeScreenViewModelFactory {
    fun create(): WelcomeScreenViewModel {
        return WelcomeScreenViewModel()
    }
}

internal class WelcomeScreenViewModel : NavigationViewModel() {
    fun onClickContinue() {
        open(RootContentScreenParams)
    }

    fun onClickSkip() {
        open(RootContentScreenParams)
    }
}
