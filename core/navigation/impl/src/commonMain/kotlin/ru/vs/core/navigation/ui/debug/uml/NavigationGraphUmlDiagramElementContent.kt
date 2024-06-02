package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Одиночный элемент узла дерева навигационного графа.
 */
@Composable
internal fun NavigationGraphUmlDiagramElementContent(
    info: NavigationGraphUmlDiagramViewState.NodeInfo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier,
        border = BorderStroke(2.dp, color = LocalContentColor.current),
    ) {
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
        }
    }
}
