package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.core.uikit.paddings.defaultCardContentPadding

@Composable
internal fun ServerContent(
    server: StateFlow<Server>,
    viewModel: ServerViewModel,
    modifier: Modifier,
) {
    val server = server.collectAsState().value
    val state = viewModel.state.collectAsState().value
    Card(modifier) {
        Column(Modifier.defaultCardContentPadding()) {
            Text("Server name = ${server.name}")
            Text(state.toString())
        }
    }
}
