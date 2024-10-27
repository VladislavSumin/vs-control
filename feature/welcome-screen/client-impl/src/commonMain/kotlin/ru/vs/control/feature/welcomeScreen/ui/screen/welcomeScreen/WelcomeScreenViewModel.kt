package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import kotlinx.coroutines.channels.Channel
import ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen.RootContentScreenParams
import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.ScreenParams

internal class WelcomeScreenViewModelFactory {
    fun create(): WelcomeScreenViewModel {
        return WelcomeScreenViewModel()
    }
}

internal class WelcomeScreenViewModel : ViewModel() {
    val navigationChannel = Channel<ScreenParams>(capacity = Channel.BUFFERED)
    fun onClickContinue() {
        navigationChannel.trySend(RootContentScreenParams)
    }

    fun onClickSkip() {
        navigationChannel.trySend(RootContentScreenParams)
    }
}
