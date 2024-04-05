package ru.vs.core.composeSharedElement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

/**
 * Хост для общих элементов, оберните общие элементы в него и при совпадении id будет проведена транзакция.
 */
@Composable
fun SharedElement(id: String, content: @Composable () -> Unit) {
    val state = LocalSharedElementHostState.current
    DisposableEffect(Unit) {
        state.registerSharedElement(id)
        onDispose {
            state.unregisterSharedElement(id)
        }
    }
    content()
}
