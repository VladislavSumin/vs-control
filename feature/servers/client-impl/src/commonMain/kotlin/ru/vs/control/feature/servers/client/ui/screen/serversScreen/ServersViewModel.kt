package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerScreenParams

@Stable
@GenerateFactory
internal class ServersViewModel(
    serversInteractor: ServersInteractor,
) : NavigationViewModel() {
    val servers = serversInteractor.observe()
        .stateIn(emptyList())

    fun onClickAddServer() = open(AddServerScreenParams)
}
