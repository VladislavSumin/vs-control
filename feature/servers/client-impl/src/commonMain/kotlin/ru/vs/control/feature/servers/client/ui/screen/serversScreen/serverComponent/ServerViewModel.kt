package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.control.feature.servers.client.domain.ServerId
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.rsub.RSubConnectionStatus

@GenerateFactory
internal class ServerViewModel(
    serversInteractor: ServersInteractor,
    @ByCreate private val serverId: ServerId,
) : ViewModel() {
    val state = serversInteractor
        .observeServerInteractor(serverId)
        .filterNotNull()
        .flatMapLatest { it.connectionStatus }
        .stateIn(RSubConnectionStatus.Connecting)
}
