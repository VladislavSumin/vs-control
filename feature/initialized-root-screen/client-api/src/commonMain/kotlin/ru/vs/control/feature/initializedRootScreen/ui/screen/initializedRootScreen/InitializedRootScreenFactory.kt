package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext

interface InitializedRootScreenFactory {
    /**
     * @param onContentReady обратный вызов, который будет вызван, когда [InitializedRootScreenComponent] будет
     * готов к отображению контента.
     */
    fun create(
        onContentReady: () -> Unit,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent
}
