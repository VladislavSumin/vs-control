package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.runtime.Stable
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import ru.vs.core.decompose.ViewModel

internal class SplashScreenViewModelFactory {
    fun create(): SplashScreenViewModel = SplashScreenViewModelImpl()
}

@Stable
internal interface SplashScreenViewModel : InstanceKeeper.Instance {
    val state: StateFlow<SplashScreenViewState>
}

internal class SplashScreenViewModelImpl : ViewModel(), SplashScreenViewModel {
    override val state = flow<SplashScreenViewState> {
        delay(LONG_LOADING_DELAY)
        emit(SplashScreenViewState.LongLoading)
    }
        .stateIn(SplashScreenViewState.FastLoading)

    companion object {
        private const val LONG_LOADING_DELAY: Long = 300L
    }
}

internal class SplashScreenViewModelPreview(isLongLoading: Boolean) : ViewModel(), SplashScreenViewModel {
    override val state: StateFlow<SplashScreenViewState> = MutableStateFlow(
        if (isLongLoading) SplashScreenViewState.LongLoading else SplashScreenViewState.FastLoading,
    )
}
