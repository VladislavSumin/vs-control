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
    // Текущее приближение графа.
    var scale by remember { mutableStateOf(1f) }

    // Текущий сдвиг графа.
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    // Корневой Box нужен, чтобы ограничить дочерний контент пределами этого элемента, а так же для обработки касаний.
    Box(
        modifier
            // Обрезаем дочерний контент границами этой ноды иначе при движении дочерний контент будет выходить за
            // пределы это ноды.
            .clipToBounds()
            .transformable(state = transformableState),
    ) {
        Tree(
            rootNode = viewModel.graph.root,
            childSelector = { it.children },
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .fillMaxSize(),
        ) {
            NavigationGraphUmlDiagramElementContent(it.info)
        }
    }
}
