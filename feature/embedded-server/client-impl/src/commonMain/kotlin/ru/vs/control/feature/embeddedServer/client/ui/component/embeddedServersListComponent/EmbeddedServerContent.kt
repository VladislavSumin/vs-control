package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServer
import ru.vs.core.uikit.paddings.defaultCardContentPadding

@Composable
internal fun EmbeddedServerContent(
    viewModel: EmbeddedServersListViewModel,
    server: EmbeddedServer,
) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.defaultCardContentPadding()) {
            Text(server.name)
            Spacer(Modifier.weight(1f))
            Menu(viewModel, server)
        }
    }
}

@Composable
private fun Menu(
    viewModel: EmbeddedServersListViewModel,
    server: EmbeddedServer,
) {
    var isOpen by remember { mutableStateOf(false) }
    IconButton(onClick = { isOpen = true }) {
        Icon(Icons.Default.MoreVert, null)
        DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = { viewModel.onClickDelete(server) },
                leadingIcon = { Icon(Icons.Default.Delete, null) },
            )
        }
    }
}
