package ru.vs.control.entities.ui.entities.unknown_entity_state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun UnknownEntityStateContent(component: UnknownEntityStateComponent) {
    val entity by component.entityState.collectAsState()
    Card(Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row {
                Text(entity.id.rawId)
                Spacer(Modifier.weight(1f))
                Text(
                    "Unknown card type",
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Text(entity.primaryState.toString())

            Text("Properties:")
            entity.properties.raw.forEach { property ->
                Text(property.toString())
            }
        }
    }
}
