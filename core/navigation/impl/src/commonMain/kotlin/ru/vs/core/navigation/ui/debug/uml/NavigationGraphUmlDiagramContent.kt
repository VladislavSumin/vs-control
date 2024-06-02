package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
internal fun NavigationGraphUmlDiagramContent(
    viewModel: NavigationGraphUmlDiagramViewModel,
    modifier: Modifier,
) {
    // Корневой Box нужен, чтобы ограничить дочерний контент пределами этого элемента.
    Box(
        modifier
            .clipToBounds()
            .background(Color.Magenta),
    ) {
        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, _ ->
            scale *= zoomChange
            offset += offsetChange * scale
        }

        SubcomposeLayout(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .transformable(state = state)
                .background(Color.Blue)
                .fillMaxSize(),
        ) { constraints ->
            val wrapContentConstraints = constraints.copy(minWidth = 0, minHeight = 0)

            val elements: List<Measurable> = subcompose(null) {
                NavigationGraphUmlDiagramElementContent(viewModel.graph.root.name)
            }

            val placeables = elements.map { it.measure(wrapContentConstraints) }

            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight,
            ) {
                placeables.forEach { it.place(0, 0) }
            }
        }
    }
}
