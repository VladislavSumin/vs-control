package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun AddServerByUrlContent(
    modifier: Modifier,
) {
    Box(modifier.background(Color.Cyan)) {
        Text("AddServerByUrlScreen", Modifier.align(Alignment.Center))
    }
}
