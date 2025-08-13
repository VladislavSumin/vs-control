package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.factoryGenerator.ByCreate
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.servers.client.domain.Server
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext

/**
 * Отображает информацию о конкретном сервере.
 */
@GenerateFactory
internal class ServerComponent(
    viewModelFactory: ServerViewModelFactory,
    @ByCreate private val server: StateFlow<Server>,
    @ByCreate context: VsComponentContext,
) : VsComponent(context), ComposeComponent {

    val key: Long get() = server.value.id.raw

    private val viewModel = viewModel { viewModelFactory.create(server.value.id) }

    @Composable
    override fun Render(modifier: Modifier) = ServerContent(server, viewModel, modifier)
}
