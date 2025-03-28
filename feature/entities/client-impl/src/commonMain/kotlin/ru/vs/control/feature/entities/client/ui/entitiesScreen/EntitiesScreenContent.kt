package ru.vs.control.feature.entities.client.ui.entitiesScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun EntitiesScreenContent(component: EntitiesScreenComponent, modifier: Modifier) {
    Scaffold(
        topBar = { TopBar() },
        modifier = modifier,
    ) {
        component.entitiesComponent.Render(Modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    // TODO
//    NavigationSupportTopAppBar(
//        title = { Text("Entities") },
//    )
}
