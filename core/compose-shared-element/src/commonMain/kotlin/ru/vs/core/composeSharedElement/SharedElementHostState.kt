package ru.vs.core.composeSharedElement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import ru.vs.core.logger.api.logger

internal val LocalSharedElementHostState = compositionLocalOf<SharedElementHostState> {
    error("LocalSharedElementHostState not set")
}

@Composable
internal fun rememberSharedElementHostState(): SharedElementHostState {
    return remember { SharedElementHostState() }
}

@Stable
internal class SharedElementHostState {
    private val logger = logger("SharedElement")

    fun registerSharedElement(id: String) {
        logger.d("Register shared element id=$id")
    }

    fun unregisterSharedElement(id: String) {
        logger.d("Unregister shared element id=$id")
    }
}
