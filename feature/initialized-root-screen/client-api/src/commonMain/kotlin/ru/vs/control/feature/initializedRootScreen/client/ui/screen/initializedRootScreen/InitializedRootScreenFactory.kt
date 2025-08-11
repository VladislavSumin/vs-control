package ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen

import kotlinx.coroutines.channels.ReceiveChannel
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.core.decompose.context.VsComponentContext

interface InitializedRootScreenFactory {
    /**
     * @param onContentReady обратный вызов, который будет вызван, когда [InitializedRootScreenComponent] будет
     * готов к отображению контента.
     * @param deeplink для передачи диплинков.
     */
    fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: VsComponentContext,
    ): ComposeComponent
}
