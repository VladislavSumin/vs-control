package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vladislavsumin.core.decompose.components.Component
import ru.vs.control.feature.servers.client.domain.Server
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factoryGenerator.ByCreate
import ru.vs.core.factoryGenerator.GenerateFactory

/**
 * Отображает информацию о конкретном сервере.
 */
@GenerateFactory
internal class ServerComponent(
    viewModelFactory: ServerViewModelFactory,
    @ByCreate private val server: StateFlow<Server>,
    @ByCreate context: ComponentContext,
) : Component(context), ComposeComponent {

    val key: Long get() = server.value.id.raw

    private val viewModel = viewModel { viewModelFactory.create(server.value.id) }

    @Composable
    override fun Render(modifier: Modifier) = ServerContent(server, viewModel, modifier)
}
