package ru.vs.core.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Logo: ImageVector
    get() {
        val current = _logo
        if (current != null) return current

        return ImageVector.Builder(
            name = "Logo",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 512.0f,
            viewportHeight = 512.0f,
            tintColor = Color.Red,
        ).apply {
            path {
                moveToRelative(dx = 146.28f, dy = 300.56f)
                lineToRelative(dx = 137.28f, dy = -137.28f)
                lineToRelative(dx = -12.36f, dy = -12.37f)
                arcToRelative(
                    a = 21.5f,
                    b = 21.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -30.39f,
                    dy1 = 0.0f,
                )
                lineToRelative(dx = -107.95f, dy = 108.0f)
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 28.28f,
                )
                close()
                moveToRelative(dx = 232.86f, dy = -41.7f)
                lineToRelative(dx = -81.8f, dy = -81.8f)
                lineToRelative(dx = -27.56f, dy = 27.55f)
                lineToRelative(dx = 95.94f, dy = 95.95f)
                lineToRelative(dx = 13.42f, dy = -13.42f)
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -28.28f,
                )
            }
            path {
                moveTo(x = 315.91f, y = 250.43f)
                verticalLineToRelative(dy = 96.71f)
                horizontalLineTo(x = 215.58f)
                verticalLineToRelative(dy = 39.0f)
                horizontalLineToRelative(dx = 119.3f)
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 20.0f,
                    dy1 = -20.0f,
                )
                verticalLineTo(y = 250.43f)
                close()
                moveToRelative(dx = -119.82f, dy = 27.89f)
                lineToRelative(dx = -39.0f, dy = 39.0f)
                verticalLineToRelative(dy = 48.83f)
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 20.0f,
                    dy1 = 20.0f,
                )
                horizontalLineToRelative(dx = 19.0f)
                verticalLineToRelative(dy = -44.21f)
                close()
            }
        }.build().also { _logo = it }
    }

@Suppress("ObjectPropertyName")
private var _logo: ImageVector? = null
