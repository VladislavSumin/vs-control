package ru.vs.core.uikit.graph

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * Рисует древовидную структуру.
 *
 * @param rootNode голова дерева.
 * @param childSelector селектор для поиска дочерних нод в родительской.
 * @param content контент одной ноды.
 * @param verticalSpace вертикальное расстояние между дочерними нодами.
 * @param horizontalSpace горизонтальное расстояние между списком родительских нод и дочерними
 *
 */
@Composable
fun <T> Tree(
    rootNode: T,
    childSelector: (T) -> List<T>,
    verticalSpace: Dp = 8.dp,
    horizontalSpace: Dp = 16.dp,
    lineColor: Color = MaterialTheme.colorScheme.onPrimary,
    lineWidth: Float = 6f,
    content: @Composable (T) -> Unit,
) {
    val drawState = remember { mutableStateOf<Points?>(null) }

    Layout(
        content = {
            Lines(drawState, lineColor, lineWidth)
            content(rootNode)
            childSelector(rootNode).forEach {
                Tree(
                    rootNode = it,
                    childSelector = childSelector,
                    verticalSpace = verticalSpace,
                    horizontalSpace = horizontalSpace,
                    content = content,
                )
            }
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
                    drawState.value = Points(
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

/**
 * Вспомогательная функция рисует линии соединяющие ноды на основе [points].
 */
@Composable
private fun Lines(
    points: State<Points?>,
    lineColor: Color,
    lineWidth: Float,
) {
    Canvas(Modifier) {
        val state = points.value ?: return@Canvas
        with(state) {
            // Линия от главного к центру
            drawLine(
                color = lineColor,
                start = Offset(endRootX.toFloat(), centerRootY.toFloat()),
                end = Offset(
                    x = endRootX.toFloat() + (startChildX - endRootX) / 2,
                    y = centerRootY.toFloat(),
                ),
                strokeWidth = lineWidth,
            )

            // Вертикальная линия
            drawLine(
                color = lineColor,
                start = Offset(
                    x = endRootX.toFloat() + (startChildX - endRootX) / 2,
                    y = centerRootY.toFloat(),
                ),
                end = Offset(
                    x = endRootX.toFloat() + (startChildX - endRootX) / 2,
                    y = childCentersY.last().toFloat(),
                ),
                strokeWidth = lineWidth,
            )

            // Горизонтальные линии к дочерним элементам
            childCentersY.forEach { childCenterY ->
                drawLine(
                    color = lineColor,
                    start = Offset(startChildX.toFloat(), childCenterY.toFloat()),
                    end = Offset(
                        x = endRootX.toFloat() + (startChildX - endRootX) / 2,
                        y = childCenterY.toFloat(),
                    ),
                    strokeWidth = lineWidth,
                )
            }
        }
    }
}

/**
 * Вспомогательный класс для передачи координат точек необходимых для построения линий из фазы measure/layout в
 * фазу draw.
 */
private data class Points(
    val endRootX: Int,
    val centerRootY: Int,
    val startChildX: Int,
    val childCentersY: List<Int>,
)
