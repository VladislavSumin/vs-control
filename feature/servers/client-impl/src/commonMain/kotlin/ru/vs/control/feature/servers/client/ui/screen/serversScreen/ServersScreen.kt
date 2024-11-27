package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent.EmbeddedServersListComponentFactory
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class ServersScreen(
    viewModelFactory: ServersViewModelFactory,
    embeddedServersListComponentFactory: EmbeddedServersListComponentFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }
    val embeddedServers = embeddedServersListComponentFactory.create(childContext("embedded_servers"))

    @Composable
    override fun Render(modifier: Modifier) = ServersContent(viewModel, embeddedServers, modifier)
}
