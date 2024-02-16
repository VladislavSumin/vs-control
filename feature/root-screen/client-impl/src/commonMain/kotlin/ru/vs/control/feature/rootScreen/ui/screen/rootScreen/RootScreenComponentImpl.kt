package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext

internal class RootScreenComponentImpl(context: ComponentContext) : RootScreenComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(color = Color.Cyan)) {
            Text("Root screen", Modifier.align(Alignment.Center))
        }
    }
}
