package ru.vs.control.feature.mainScreen.ui.screen.mainScreen

import androidx.compose.runtime.Stable
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.servers.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class MainViewModel : NavigationViewModel() {
//    val state: StateFlow<MainViewState> = TODO()
//    val events: Channel<MainEvents> = TODO()

    fun onClickDebug() = open(DebugScreenParams)
    fun onClickAddServer() = open(AddServerScreenParams)
}
