package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Stable
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class ServersViewModel(
    serversInteractor: ServersInteractor,
) : NavigationViewModel() {
    val servers = serversInteractor.observe()
        .stateIn(emptyList())

    fun onClickAddServer() = open(AddServerScreenParams)
}
