package ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.core.decompose.context.VsComponentContext

interface RootScreenFactory {
    /**
     * @param deeplink канал с входящими диплинками.
     */
    fun create(
        context: VsComponentContext,
        deeplink: ReceiveChannel<String> = Channel(),
    ): ComposeComponent
}
