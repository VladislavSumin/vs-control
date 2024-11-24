package ru.vs.core.navigation.ui.debug.navigationHost

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.host.HostType

private val STROKE_WIDTH = 6.dp

internal fun ComposeComponent.wrapWithNavigationHostDebugOverlay(
    hostType: HostType,
) = object : ComposeComponent {
    @Composable
    override fun Render(modifier: Modifier) {
        this@wrapWithNavigationHostDebugOverlay.Render(modifier.navigationHostDebugOverlay(hostType.color))
    }
}

private fun Modifier.navigationHostDebugOverlay(
    color: Color,
) = this.drawWithContent {
    drawContent()
    drawOutline(
        outline = Outline.Rectangle(
            Rect(
                Offset(STROKE_WIDTH.toPx() / 2, STROKE_WIDTH.toPx() / 2),
                Size(size.width - STROKE_WIDTH.toPx(), size.height - STROKE_WIDTH.toPx()),
            ),
        ),
        color = color,
        style = Stroke(width = STROKE_WIDTH.toPx()),
    )
}.padding(STROKE_WIDTH)

private val HostType.color: Color
    get() = when (this) {
        HostType.ROOT -> Color.Magenta
        HostType.STACK -> Color.Red
        HostType.SLOT -> Color.Blue
        HostType.PAGES -> Color.Green
    }
