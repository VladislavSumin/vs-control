package ru.vs.control.feature.serverInfo.server.rsub

import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.serverInfo.ServerInfo
import ru.vs.control.feature.serverInfo.rSub.ServerInfoRsub
import ru.vs.control.feature.serverInfo.server.domain.ServerInfoInteractor

internal class ServerInfoRsubImpl(
    private val serverInfoInteractor: ServerInfoInteractor,
) : ServerInfoRsub {
    override fun observeServerInfo(): Flow<ServerInfo> {
        return serverInfoInteractor.observeServerInfo()
    }
}
