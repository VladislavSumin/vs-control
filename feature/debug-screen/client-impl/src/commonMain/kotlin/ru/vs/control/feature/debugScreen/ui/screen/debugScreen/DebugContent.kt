package ru.vs.control.feature.debugScreen.ui.screen.debugScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.decompose.ComposeComponent

@Composable
internal fun DebugContent(
    umlComponent: ComposeComponent,
    modifier: Modifier,
) {
    Box(modifier.systemBarsPadding()) {
        Column {
            Text("DebugScreen")
            umlComponent.Render(Modifier.fillMaxSize())
        }
    }
}
