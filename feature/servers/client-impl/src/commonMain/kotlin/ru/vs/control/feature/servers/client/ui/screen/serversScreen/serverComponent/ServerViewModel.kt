package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import kotlinx.coroutines.flow.filterNotNull
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor.ConnectionStatusWithServerInfo
import ru.vs.control.feature.servers.client.domain.ServerId

@GenerateFactory
internal class ServerViewModel(
    serverInfoInteractor: ServerInfoInteractor,
    @ByCreate private val serverId: ServerId,
) : ViewModel() {
    val state = serverInfoInteractor
        .observeConnectionStatusWithServerInfo(serverId)
        .filterNotNull()
        .stateIn(ConnectionStatusWithServerInfo.Connecting)
}
