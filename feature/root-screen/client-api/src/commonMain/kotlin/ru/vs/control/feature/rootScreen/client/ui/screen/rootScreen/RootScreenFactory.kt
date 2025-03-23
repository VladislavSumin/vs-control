package ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

interface RootScreenFactory {
    /**
     * @param deeplink канал с входящими диплинками.
     */
    fun create(
        context: ComponentContext,
        deeplink: ReceiveChannel<String> = Channel(),
    ): ComposeComponent
}
