package ru.vs.control.feature.mainScreen.ui.screen.mainScreen

import androidx.compose.runtime.Stable
import ru.vs.core.decompose.ViewModel

internal class MainViewModelFactory {
    fun create(): MainViewModel {
        return MainViewModel()
    }
}

@Stable
internal class MainViewModel : ViewModel() {
//    val state: StateFlow<MainViewState> = TODO()
//    val events: Channel<MainEvents> = TODO()
}
