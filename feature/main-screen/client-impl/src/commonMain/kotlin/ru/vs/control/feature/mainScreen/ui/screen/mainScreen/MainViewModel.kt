package ru.vs.control.feature.mainScreen.ui.screen.mainScreen

import androidx.compose.runtime.Stable
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.core.navigation.viewModel.NavigationViewModel

internal class MainViewModelFactory {
    fun create(): MainViewModel {
        return MainViewModel()
    }
}

@Stable
internal class MainViewModel : NavigationViewModel() {
//    val state: StateFlow<MainViewState> = TODO()
//    val events: Channel<MainEvents> = TODO()

    fun onClickDebug() = open(DebugScreenParams)
}
