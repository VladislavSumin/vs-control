package ru.vs.core.navigation.ui.debug.uml

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
import androidx.compose.ui.graphics.graphicsLayer
import ru.vs.core.uikit.graph.Tree

@Composable
internal fun NavigationGraphUmlDiagramContent(
    viewModel: NavigationGraphUmlDiagramViewModel,
    modifier: Modifier,
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    // Корневой Box нужен, чтобы ограничить дочерний контент пределами этого элемента, а так же для обработки касаний
    Box(
        modifier
            .clipToBounds()
            .transformable(state = state),
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .fillMaxSize(),
        ) {
            Tree(
                rootNode = viewModel.graph.root,
                childSelector = { it.children },
            ) {
                NavigationGraphUmlDiagramElementContent(it.info)
            }
        }
    }
}
