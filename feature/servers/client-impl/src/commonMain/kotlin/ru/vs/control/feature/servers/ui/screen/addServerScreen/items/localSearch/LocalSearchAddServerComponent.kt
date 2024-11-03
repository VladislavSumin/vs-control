package ru.vs.control.feature.servers.ui.screen.addServerScreen.items.localSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

class LocalSearchAddServerComponent(
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Card(modifier) {
            Row(
                Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Default.Lan, null)
                Column(Modifier.padding(start = 8.dp)) {
                    Text(
                        "Поиск серверов локальной сети...",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        "... поиск ...",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
