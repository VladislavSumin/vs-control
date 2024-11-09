package ru.vs.control.feature.servers.ui.screen.serversScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent.EmbeddedServersListComponent

@Composable
internal fun ServersContent(
    viewModel: ServersViewModel,
    embeddedServers: EmbeddedServersListComponent,
    modifier: Modifier,
) {
    Scaffold(
        modifier,
        topBar = { AppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onClickAddServer) {
                Icon(Icons.Default.Add, null)
            }
        },
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            embeddedServers.renderIn(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text("Servers")
        },
    )
}
