package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vs.core.decompose.ViewModel
import ru.vs.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class SplashScreenViewModel : ViewModel() {
    val state = flow<SplashScreenViewState> {
        delay(LONG_LOADING_DELAY)
        emit(SplashScreenViewState.LongLoading)
    }
        .stateIn(SplashScreenViewState.FastLoading)

    companion object {
        private const val LONG_LOADING_DELAY: Long = 300L
    }
}
