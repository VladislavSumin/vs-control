package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel

interface InitializedRootScreenFactory {
    /**
     * @param onContentReady обратный вызов, который будет вызван, когда [InitializedRootScreenComponent] будет
     * готов к отображению контента.
     * @param deeplink для передачи диплинков.
     */
    fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent
}
