package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Одиночный элемент узла дерева навигационного графа.
 */
@Composable
internal fun NavigationGraphUmlDiagramElementContent(
    info: NavigationGraphUmlNode.Info,
    modifier: Modifier = Modifier,
) {
    val finalModifier = if (!info.isPartOfMainGraph) {
        modifier.dashedBorder(2.dp, MaterialTheme.colorScheme.outlineVariant, 12.dp)
    } else {
        modifier
    }

    Card(
        modifier = finalModifier,
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation(),
        border = if (info.isPartOfMainGraph) CardDefaults.outlinedCardBorder() else null,
    ) {
        Column(
            Modifier
                .width(IntrinsicSize.Min)
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp,
                ),
        ) {
            Text(
                info.name,
                style = MaterialTheme.typography.titleMedium,
            )
            if (info.isPartOfMainGraph) {
                Text(
                    "hasDefaultParams=${info.hasDefaultParams}",
                    style = if (info.hasDefaultParams) {
                        MaterialTheme.typography.bodyMedium
                    } else {
                        MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                )
            }
            if (info.description != null) {
                Text(
                    info.description,
                    Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Suppress("MagicNumber")
private fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 8f), 0f),
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx),
                    )
                }
            },
        )
    },
)
