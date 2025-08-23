package ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

@Composable
internal fun EntitiesContent(
    modifier: Modifier,
    entitiesComponent: ComposeComponent,
) {
    entitiesComponent.Render(modifier)
}
