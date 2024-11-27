package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Stable
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class ServersViewModel : NavigationViewModel() {
//    val state: StateFlow<ServersViewState> = TODO()
//    val events: Channel<ServersEvents> = TODO()

    fun onClickAddServer() = open(AddServerScreenParams)
}
