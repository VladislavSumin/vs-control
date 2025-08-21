package ru.vs.control.feature.serverInfo.rSub

import kotlinx.coroutines.flow.Flow
import ru.vs.control.feature.serverInfo.ServerInfo
import ru.vs.rsub.RSubInterface

@RSubInterface
interface ServerInfoRsub {
    fun observeServerInfo(): Flow<ServerInfo>
}
