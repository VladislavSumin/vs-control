package ru.vs.control.entities.ui.entities_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.NavigationSupportTopAppBar

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
    NavigationSupportTopAppBar(
        title = { Text("Entities") },
    )
}
