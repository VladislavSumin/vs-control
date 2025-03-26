package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class MainViewModel : NavigationViewModel() {
//    val state: StateFlow<MainViewState> = TODO()
//    val events: Channel<MainEvents> = TODO()

    fun onClickDebug() = open(DebugScreenParams)
}
