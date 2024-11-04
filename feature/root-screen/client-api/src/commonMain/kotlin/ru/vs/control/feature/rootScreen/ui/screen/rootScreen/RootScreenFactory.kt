package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vs.core.decompose.ComposeComponent

interface RootScreenFactory {
    /**
     * @param deeplink канал с входящими диплинками.
     */
    fun create(
        context: ComponentContext,
        deeplink: ReceiveChannel<String> = Channel(),
    ): ComposeComponent
}
