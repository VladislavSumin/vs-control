package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import kotlinx.coroutines.flow.filterNotNull
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor.ConnectionStatusWithServerInfo
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.control.feature.servers.client.domain.ServersInteractor

@GenerateFactory
internal class ServerViewModel(
    serversInteractor: ServersInteractor,
    serverInfoInteractor: ServerInfoInteractor,
    @ByCreate private val serverId: ServerId,
) : ViewModel() {
    val state = serversInteractor.withServerInteractor(serverId) {
        serverInfoInteractor.observeConnectionStatusWithServerInfo()
    }
        .filterNotNull() // TODO
        .stateIn(ConnectionStatusWithServerInfo.Connecting)
}
