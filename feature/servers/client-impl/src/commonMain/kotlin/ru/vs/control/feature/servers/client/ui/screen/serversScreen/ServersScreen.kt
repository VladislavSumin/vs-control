package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext
import ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent.ServerComponentFactory
import ru.vs.core.decompose.router.list.childListWithState
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory

@GenerateScreenFactory
internal class ServersScreen(
    viewModelFactory: ServersViewModelFactory,
    embeddedServersListComponentFactory: EmbeddedServersListComponentFactory,
    serverComponentFactory: ServerComponentFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val embeddedServers = embeddedServersListComponentFactory.create(childContext("embedded_servers"))
    private val servers = childListWithState(
        state = viewModel.servers.asValue(),
        idSelector = { it.id },
        childFactory = { server, context -> serverComponentFactory.create(server, context) },
    )

    @Composable
    override fun Render(modifier: Modifier) = ServersContent(viewModel, embeddedServers, servers, modifier)
}
