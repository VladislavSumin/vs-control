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
import androidx.compose.ui.unit.Constraints
import kotlin.math.max

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

        Box(
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
        ) {
            RecursiveElementGroup(viewModel.graph.root)
        }
    }
}

@Composable
private fun RecursiveElementGroup(node: NavigationGraphUmlDiagramViewState.Node) {
    SubcomposeLayout { constraints ->
        // Так как RecursiveElementGroup должна помещаться в scale контейнер, то мы никак не ограничиваем
        // размеры дочерних элементов
        val infiniteWrapContentConstraints = Constraints()

        val children: List<Measurable> = subcompose("children") {
            node.children.forEach { child ->
                RecursiveElementGroup(child)
            }
        }

        val root = subcompose("root") {
            NavigationGraphUmlDiagramElementContent(node.name)
        }.single()

        val childrenPlaceable = children.map { it.measure(infiniteWrapContentConstraints) }
        val rootPlaceable = root.measure(infiniteWrapContentConstraints)

        layout(
            width = rootPlaceable.width + (childrenPlaceable.maxOfOrNull { it.width } ?: 0),
            height = max(rootPlaceable.height, childrenPlaceable.sumOf { it.height }),
        ) {
            rootPlaceable.place(0, 0)
            var currentHeight = 0
            childrenPlaceable.forEach {
                it.place(rootPlaceable.width, currentHeight)
                currentHeight += it.height
            }
        }
    }
}
