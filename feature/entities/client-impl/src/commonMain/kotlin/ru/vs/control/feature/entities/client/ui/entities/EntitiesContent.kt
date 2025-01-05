package ru.vs.control.feature.entities.client.ui.entities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
internal fun EntitiesContent(component: EntitiesComponent) {
    val state by component.entitiesList.subscribeAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(state) { child ->
            child.Render(Modifier)
        }
    }
}
