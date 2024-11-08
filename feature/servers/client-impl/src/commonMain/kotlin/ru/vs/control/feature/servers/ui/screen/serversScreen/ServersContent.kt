package ru.vs.control.feature.servers.ui.screen.serversScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun ServersContent(
    modifier: Modifier,
) {
    Box(modifier.background(Color.Cyan)) {
        Text("ServersScreen", Modifier.align(Alignment.Center))
    }
}
