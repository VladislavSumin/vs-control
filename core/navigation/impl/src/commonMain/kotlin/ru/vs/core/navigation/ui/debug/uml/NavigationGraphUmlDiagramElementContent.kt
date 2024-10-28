package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Одиночный элемент узла дерева навигационного графа.
 */
@Composable
internal fun NavigationGraphUmlDiagramElementContent(
    info: NavigationGraphUmlNode.Info,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier) {
        Column(
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        ) {
            Text(
                info.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                "hasDefaultParams=${info.hasDefaultParams}",
                style = if (info.hasDefaultParams) {
                    MaterialTheme.typography.bodyMedium
                } else {
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
        }
    }
}
