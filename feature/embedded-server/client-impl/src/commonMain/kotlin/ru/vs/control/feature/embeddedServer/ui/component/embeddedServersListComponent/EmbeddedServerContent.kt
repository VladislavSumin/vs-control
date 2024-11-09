package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.core.uikit.paddings.defaultCardContentPadding

@Composable
internal fun EmbeddedServerContent(server: EmbeddedServer) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.defaultCardContentPadding()) {
            Text(server.name)
        }
    }
}
