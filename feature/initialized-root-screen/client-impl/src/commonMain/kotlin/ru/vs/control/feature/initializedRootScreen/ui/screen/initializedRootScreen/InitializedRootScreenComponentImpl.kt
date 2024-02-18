package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext

internal class InitializedRootScreenComponentImpl(
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier) {
            Text("Initialized root screen", Modifier.align(Alignment.Center))
        }
    }
}
