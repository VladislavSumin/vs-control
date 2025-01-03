package ru.vs.control.feature.servers.client.ui.screen.serversScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.Value
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent.ServerComponent
import ru.vs.core.decompose.LazyListComponent

@Composable
internal fun ServersContent(
    viewModel: ServersViewModel,
    embeddedServers: LazyListComponent,
    servers: Value<List<ServerComponent>>,
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
        val embeddedServersState = embeddedServers.rememberRenderer()
        val servers = servers.subscribeAsState().value
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            embeddedServersState.renderIn(this)
            items(servers, key = { it.key }) {
                it.Render(Modifier.fillMaxWidth())
            }
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
