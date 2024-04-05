package ru.vs.core.composeSharedElement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Хост для всех shared элементов. Достаточно одного инстанса на приложение
 */
@Composable
fun SharedElementHost(content: @Composable () -> Unit) {
    val state = rememberSharedElementHostState()
    CompositionLocalProvider(
        LocalSharedElementHostState provides state,
        content = content,
    )
}
