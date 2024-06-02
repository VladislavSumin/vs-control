package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.Canvas
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
@Suppress("LongMethod", "MagicNumber")
@Composable
private fun RecursiveElementGroup(
    node: NavigationGraphUmlDiagramViewState.Node,
    verticalSpace: Dp = 8.dp,
    horizontalSpace: Dp = 16.dp,
) {
    data class Points(
        val endRootX: Int,
        val centerRootY: Int,
        val startChildX: Int,
        val childCentersY: List<Int>,
    )

    var drawState by remember { mutableStateOf<Points?>(null) }

    val lineColor = Color.Red
    val strokeWidth = 4f

    Layout(
        content = {
            Canvas(Modifier) {
                val state = drawState ?: return@Canvas

                // Линия от главного к центру
                drawLine(
                    color = lineColor,
                    start = Offset(state.endRootX.toFloat(), state.centerRootY.toFloat()),
                    end = Offset(
                        x = state.endRootX.toFloat() + (state.startChildX - state.endRootX) / 2,
                        y = state.centerRootY.toFloat(),
                    ),
                    strokeWidth = strokeWidth,
                )

                // Вертикальная линия
                drawLine(
                    color = lineColor,
                    start = Offset(
                        x = state.endRootX.toFloat() + (state.startChildX - state.endRootX) / 2,
                        y = state.centerRootY.toFloat(),
                    ),
                    end = Offset(
                        x = state.endRootX.toFloat() + (state.startChildX - state.endRootX) / 2,
                        y = state.childCentersY.last().toFloat(),
                    ),
                    strokeWidth = strokeWidth,
                )

                // Горизонтальные линии к дочерним элементам
                state.childCentersY.forEach { childCenterY ->
                    drawLine(
                        color = lineColor,
                        start = Offset(state.startChildX.toFloat(), childCenterY.toFloat()),
                        end = Offset(
                            x = state.endRootX.toFloat() + (state.startChildX - state.endRootX) / 2,
                            y = childCenterY.toFloat(),
                        ),
                        strokeWidth = strokeWidth,
                    )
                }
            }
            NavigationGraphUmlDiagramElementContent(
                name = node.name,
            )
            node.children.forEach { RecursiveElementGroup(it) }
        },
        measurePolicy = { children, constraints ->
            val hasChildNodes = children.size > 2

            // Так как RecursiveElementGroup должна помещаться в scale контейнер, то мы никак не ограничиваем
            // размеры дочерних элементов
            val infiniteWrapContentConstraints = Constraints()

            // Canvas для отрисовки вспомогательных элементов
            val canvasMeasurable = children[0]

            // Главный элемент, представляет собой переданную node. Всегда строго один.
            val rootMeasurable = children[1]
            val root = rootMeasurable.measure(if (hasChildNodes) infiniteWrapContentConstraints else constraints)

            // Дочерние элементы.
            val childNodesMeasurable: List<Measurable> = children.drop(2)

            val childNodesConstraints = Constraints(
                minWidth = (childNodesMeasurable.maxOfOrNull { it.maxIntrinsicWidth(Int.MAX_VALUE) } ?: 0),
            )
            val childNodes = childNodesMeasurable.map { it.measure(childNodesConstraints) }

            // Вычисляем всякие отступы.
            val horizontalSpacePx = if (childNodes.isEmpty()) 0 else horizontalSpace.toPx().toInt()
            val verticalSpacePx = verticalSpace.toPx().toInt()
            val childrenTotalSpacePx = max(verticalSpacePx * (childNodes.size - 1), 0)

            val width = root.width + (childNodes.maxOfOrNull { it.width } ?: 0) + childrenTotalSpacePx
            val height = max(root.height, childNodes.sumOf { it.height }) + horizontalSpacePx

            val canvas = canvasMeasurable.measure(Constraints.fixed(width, height))

            layout(
                width = width,
                height = height,
            ) {
                root.place(0, 0)
                canvas.place(0, 0)
                var currentHeight = 0
                val childCentersY = childNodes.map {
                    it.place(root.width + horizontalSpacePx, currentHeight)
                    val centerY = currentHeight + it.height / 2
                    currentHeight += it.height + verticalSpacePx
                    centerY
                }
                if (hasChildNodes) {
                    drawState = Points(
                        endRootX = root.width,
                        centerRootY = root.height / 2,
                        startChildX = root.width + horizontalSpacePx,
                        childCentersY = childCentersY,
                    )
                }
            }
        },
    )
}
