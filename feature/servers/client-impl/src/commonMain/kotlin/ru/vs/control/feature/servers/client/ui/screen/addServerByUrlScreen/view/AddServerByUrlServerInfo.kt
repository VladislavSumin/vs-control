package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.control.feature.serverInfo.client.domain.ServerInfo

@Composable
internal fun AddServerByUrlServerInfo(
    serverInfo: ServerInfo,
    url: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier) {
        Column {
            Text(serverInfo.name)
            Text(url)
            Text("_version: ${serverInfo.version}")
        }
    }
}
