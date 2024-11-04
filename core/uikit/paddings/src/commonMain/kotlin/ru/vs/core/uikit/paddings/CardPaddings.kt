package ru.vs.core.uikit.paddings

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Отступы для контента внутри карточек по умолчанию.
 */
fun Modifier.defaultCardContentPadding() = padding(
    horizontal = 16.dp,
    vertical = 8.dp,
)
