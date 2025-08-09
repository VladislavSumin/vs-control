package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactory
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent.ServerComponentFactory
import ru.vs.core.decompose.router.list.childListWithState

@GenerateScreenFactory
internal class ServersScreen(
    viewModelFactory: ServersViewModelFactory,
    embeddedServersListComponentFactory: EmbeddedServersListComponentFactory,
    serverComponentFactory: ServerComponentFactory,
    context: ComponentContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val embeddedServers = embeddedServersListComponentFactory.create(context.childContext("embedded_servers"))
    private val servers = context.childListWithState(
        state = viewModel.servers.asValue(),
        idSelector = { it.id },
        childFactory = { server, context -> serverComponentFactory.create(server, context) },
    )

    @Composable
    override fun Render(modifier: Modifier) = ServersContent(viewModel, embeddedServers, servers, modifier)
}
