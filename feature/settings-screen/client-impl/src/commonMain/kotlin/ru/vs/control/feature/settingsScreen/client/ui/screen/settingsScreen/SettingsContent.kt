package ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun SettingsContent(
    modifier: Modifier,
) {
    Box(modifier.background(Color.Cyan)) {
        Text("SettingsScreen", Modifier.align(Alignment.Center))
    }
}
