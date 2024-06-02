package ru.vs.core.uikit.graph

import androidx.compose.foundation.Canvas
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
 * @param verticalSpace вертикальное расстояние между родительской нодой и дочерними.
 * @param horizontalSpace горизонтальное расстояние между дочерними нодами.
 */
@Composable
fun <T> Tree(
    rootNode: T,
    childSelector: (T) -> List<T>,
    verticalSpace: Dp = 24.dp,
    horizontalSpace: Dp = 16.dp,
    lineColor: Color = LocalContentColor.current,
    lineWidth: Dp = 2.dp,
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
        measurePolicy = { children, _ ->
            val hasChildNodes = children.size > 2

            // Так как RecursiveElementGroup должна помещаться в scale контейнер, то мы никак не ограничиваем
            // размеры дочерних элементов
            val infiniteWrapContentConstraints = Constraints()

            // Canvas для отрисовки вспомогательных элементов
            val canvasMeasurable = children[0]

            // Главный элемент, представляет собой переданную node. Всегда строго один.
            val rootMeasurable = children[1]
            val root = rootMeasurable.measure(infiniteWrapContentConstraints)

            // Дочерние элементы.
            val childNodesMeasurable: List<Measurable> = children.drop(2)

            val childNodes = childNodesMeasurable.map { it.measure(infiniteWrapContentConstraints) }

            // Вычисляем всякие отступы.
            val horizontalSpacePx = horizontalSpace.toPx().toInt()
            val childrenTotalSpacePx = max(horizontalSpacePx * (childNodes.size - 1), 0)
            val verticalSpacePx = if (childNodes.isEmpty()) 0 else verticalSpace.toPx().toInt()

            val width = max(root.width, childNodes.sumOf { it.width }) + childrenTotalSpacePx
            val height = root.height + (childNodes.maxOfOrNull { it.height } ?: 0) + verticalSpacePx

            val canvas = canvasMeasurable.measure(Constraints.fixed(width, height))

            layout(
                width = width,
                height = height,
            ) {
                root.place(width / 2 - root.width / 2, 0)
                canvas.place(0, 0)
                var currentWidth = 0
                val childCentersX = childNodes.map {
                    it.place(currentWidth, root.height + verticalSpacePx)
                    val centerX = currentWidth + it.width / 2
                    currentWidth += it.width + horizontalSpacePx
                    centerX
                }
                if (hasChildNodes) {
                    drawState.value = Points(
                        endRootY = root.height,
                        centerRootX = width / 2,
                        startChildY = root.height + verticalSpacePx,
                        childCentersX = childCentersX,
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
    lineWidth: Dp,
) {
    Canvas(Modifier) {
        val lineWidthPx = lineWidth.toPx()
        val state = points.value ?: return@Canvas
        with(state) {
            // Линия от главного к центру
            drawLine(
                color = lineColor,
                start = Offset(
                    x = centerRootX.toFloat(),
                    y = endRootY.toFloat(),
                ),
                end = Offset(
                    x = centerRootX.toFloat(),
                    y = endRootY.toFloat() + (startChildY - endRootY) / 2,
                ),
                strokeWidth = lineWidthPx,
                cap = StrokeCap.Round,
            )

            // Горизонтальная линия
            drawLine(
                color = lineColor,
                start = Offset(
                    x = childCentersX.first().toFloat(),
                    y = endRootY.toFloat() + (startChildY - endRootY) / 2,
                ),
                end = Offset(
                    x = childCentersX.last().toFloat(),
                    y = endRootY.toFloat() + (startChildY - endRootY) / 2,
                ),
                strokeWidth = lineWidthPx,
                cap = StrokeCap.Round,
            )

            // Вертикальные линии к дочерним элементам
            childCentersX.forEach { childCenterX ->
                drawLine(
                    color = lineColor,
                    start = Offset(
                        x = childCenterX.toFloat(),
                        y = endRootY.toFloat() + (startChildY - endRootY) / 2,
                    ),
                    end = Offset(
                        x = childCenterX.toFloat(),
                        y = startChildY.toFloat(),
                    ),
                    strokeWidth = lineWidthPx,
                    cap = StrokeCap.Round,
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
    val endRootY: Int,
    val centerRootX: Int,
    val startChildY: Int,
    val childCentersX: List<Int>,
)
