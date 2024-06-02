package ru.vs.core.navigation.ui.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.core.decompose.ComposeComponent

class NavigationGraphUmlDiagramComponentFactory internal constructor(
    private val viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
) {
    fun create(context: ComponentContext): ComposeComponent {
        return NavigationGraphUmlDiagramComponent(
            viewModelFactory,
            context,
        )
    }
}

/**
 * Отображает текущий граф навигации в удобной для человека форме.
 */
internal class NavigationGraphUmlDiagramComponent(
    viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
    context: ComponentContext,
) : ComponentContext by context, ComposeComponent {
    private val viewModel: NavigationGraphUmlDiagramViewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        // set up all transformation states
        var scale by remember { mutableStateOf(1f) }
        var rotation by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange
        }
        Box(
            modifier
                // apply other transformations like rotation and zoom
                // on the pizza slice emoji
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state)
                .background(Color.Blue)
                .fillMaxSize(),
        ) {
            Text(viewModel.test.screenRegistration.nameForLogs)
        }
    }
}
