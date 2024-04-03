package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vs.core.decompose.ViewModel

internal class SplashScreenViewModelFactory {
    fun create(): SplashScreenViewModel = SplashScreenViewModel()
}

internal class SplashScreenViewModel : ViewModel() {
    val state = flow<SplashScreenViewState> {
        delay(LONG_LOADING_DELAY)
        emit(SplashScreenViewState.LongLoading)
    }
        .stateIn(SplashScreenViewState.FastLoading)

    companion object {
        private const val LONG_LOADING_DELAY: Long = 1000L
    }
}
