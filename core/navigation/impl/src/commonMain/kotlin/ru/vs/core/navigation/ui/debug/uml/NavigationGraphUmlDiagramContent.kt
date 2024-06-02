package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

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
            .transformable(state = state)
            .background(Color.Magenta),
    ) {

        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
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
    Layout(
        content = {
            NavigationGraphUmlDiagramElementContent(
                name = node.name,
            )
            node.children.forEach { RecursiveElementGroup(it) }
        },
        modifier = Modifier.border(2.dp, Color.Red),
        measurePolicy = { children, constraints ->
            val hasChildNodes = children.size > 1

            // Так как RecursiveElementGroup должна помещаться в scale контейнер, то мы никак не ограничиваем
            // размеры дочерних элементов
            val infiniteWrapContentConstraints = Constraints()

            // Главный элемент, представляет собой переданную node. Всегда строго один.
            val rootMeasurable = children.first()
            val root = rootMeasurable.measure(if (hasChildNodes) infiniteWrapContentConstraints else constraints)

            // Дочерние элементы.
            val childNodesMeasurable: List<Measurable> = children.drop(1)

            val childNodesConstraints = Constraints(
                minWidth = (childNodesMeasurable.maxOfOrNull { it.maxIntrinsicWidth(Int.MAX_VALUE) } ?: 0),
            )
            val childNodes = childNodesMeasurable.map { it.measure(childNodesConstraints) }

            // Вычисляем всякие отступы.
            val horizontalSpacePx = if (childNodes.isEmpty()) 0 else horizontalSpace.toPx().toInt()
            val verticalSpacePx = verticalSpace.toPx().toInt()
            val childrenTotalSpacePx = max(verticalSpacePx * (childNodes.size - 1), 0)

            layout(
                width = root.width + (childNodes.maxOfOrNull { it.width } ?: 0) + childrenTotalSpacePx,
                height = max(root.height, childNodes.sumOf { it.height }) + horizontalSpacePx,
            ) {
                root.place(0, 0)
                var currentHeight = 0
                childNodes.forEach {
                    it.place(root.width + horizontalSpacePx, currentHeight)
                    currentHeight += it.height + verticalSpacePx
                }
            }
        },
    )
}
