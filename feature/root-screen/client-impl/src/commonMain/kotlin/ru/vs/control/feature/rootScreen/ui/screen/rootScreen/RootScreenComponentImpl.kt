package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate

internal class RootScreenComponentImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    context: ComponentContext,
) : RootScreenComponent, ComponentContext by context {
    private val viewModel = instanceKeeper.getOrCreate { rootScreenViewModelFactory.create() }

    init {
        // TODO
        viewModel
    }

    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(color = Color.Cyan)) {
            Text("Root screen", Modifier.align(Alignment.Center))
        }
    }
}
