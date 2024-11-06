package ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun AddEmbeddedServerContent(
    modifier: Modifier,
) {
    Box(modifier.background(Color.Cyan)) {
        Text("AddEmbeddedServerScreen", Modifier.align(Alignment.Center))
    }
}
