package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.serverInfo.client.domain.ServerInfo
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.core.uikit.paddings.defaultCardContentPadding

@Composable
internal fun AddServerByUrlServerInfo(
    serverInfo: ServerInfo,
    url: String,
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit = {},
) {
    Card(modifier) {
        Row(
            Modifier.defaultCardContentPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .padding(end = 16.dp)
                    .weight(1f),
            ) {
                Text(
                    serverInfo.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    AddServerByUrlViewModel.SCHEME + url,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    "_version: ${serverInfo.version}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            IconButton(onClick = onClickEdit) {
                Icon(Icons.Default.Edit, null)
            }
        }
    }
}
