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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

/**
 * @param verticalSpace вертикальное расстояние между дочерними нодами.
 * @param horizontalSpace горизонтальное расстояние между списком родительских нод и дочерними
 */
@Composable
private fun RecursiveElementGroup(
    node: NavigationGraphUmlDiagramViewState.Node,
    verticalSpace: Dp = 8.dp,
    horizontalSpace: Dp = 16.dp,
) {
    SubcomposeLayout { _ ->
        // Так как RecursiveElementGroup должна помещаться в scale контейнер, то мы никак не ограничиваем
        // размеры дочерних элементов
        val infiniteWrapContentConstraints = Constraints()

        // Главный элемент, представляет собой переданную node. Всегда строго один.
        val rootMeasurable = subcompose("root") {
            NavigationGraphUmlDiagramElementContent(node.name)
        }.single()
        val root = rootMeasurable.measure(infiniteWrapContentConstraints)

        // Дочерние элементы.
        val childrenMeasurable: List<Measurable> = subcompose("children") {
            node.children.forEach { child ->
                RecursiveElementGroup(child)
            }
        }
        val children = childrenMeasurable.map { it.measure(infiniteWrapContentConstraints) }

        // Вычисляем всякие отступы.
        val horizontalSpacePx = if (children.isEmpty()) 0 else horizontalSpace.toPx().toInt()
        val verticalSpacePx = verticalSpace.toPx().toInt()
        val childrenTotalSpacePx = max(verticalSpacePx * (children.size - 1), 0)

        layout(
            width = root.width + (children.maxOfOrNull { it.width } ?: 0) + childrenTotalSpacePx,
            height = max(root.height, children.sumOf { it.height }) + horizontalSpacePx,
        ) {
            root.place(0, 0)
            var currentHeight = 0
            children.forEach {
                it.place(root.width + horizontalSpacePx, currentHeight)
                currentHeight += it.height + verticalSpacePx
            }
        }
    }
}
