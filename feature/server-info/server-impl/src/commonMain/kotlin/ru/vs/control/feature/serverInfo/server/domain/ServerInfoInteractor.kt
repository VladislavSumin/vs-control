package ru.vs.control.feature.serverInfo.server.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.feature.serverInfo.ServerInfo

internal interface ServerInfoInteractor {
    fun observeServerInfo(): Flow<ServerInfo>
}

internal class ServerInfoInteractorImpl : ServerInfoInteractor {
    override fun observeServerInfo(): Flow<ServerInfo> {
        return flowOf(ServerInfo("Control Test Server", "0.0.1"))
    }
}
