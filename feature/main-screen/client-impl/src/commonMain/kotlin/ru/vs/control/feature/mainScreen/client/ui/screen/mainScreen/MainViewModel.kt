package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen.DebugScreenParams
import ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen.EntitiesScreenParams
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams

@Stable
@GenerateFactory
internal class MainViewModel : NavigationViewModel() {
    fun onClickEntities() = open(EntitiesScreenParams)
    fun onClicksServers() = open(ServersScreenParams)
    fun onClickDebug() = open(DebugScreenParams)
}
