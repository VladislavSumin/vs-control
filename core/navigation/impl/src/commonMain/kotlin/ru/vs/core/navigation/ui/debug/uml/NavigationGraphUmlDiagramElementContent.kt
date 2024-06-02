package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Одиночный элемент узла дерева навигационного графа.
 */
@Composable
internal fun NavigationGraphUmlDiagramElementContent(
    name: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier) {
        Column(
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        ) {
            Text(name)
        }
    }
}
