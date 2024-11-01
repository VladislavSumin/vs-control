package ru.vs.core.navigation

import kotlinx.coroutines.channels.Channel
import ru.vs.core.collections.tree.find
import ru.vs.core.navigation.serializer.NavigationSerializer
import ru.vs.core.navigation.tree.NavigationTree

/**
 * Точка входа в навигацию, она же глобальный навигатор.
 */
class Navigation internal constructor(
    internal val navigationTree: NavigationTree,
    internal val navigationSerializer: NavigationSerializer,
) {
    internal val navigationChannel = Channel<NavigationEvent>(Channel.BUFFERED)

    fun open(screenParams: ScreenParams) = send(NavigationEvent.Open(screenParams))
    fun close(screenParams: ScreenParams) = send(NavigationEvent.Close(screenParams))

    private fun send(event: NavigationEvent) {
        navigationChannel.trySend(event).getOrThrow()
    }

    fun findDefaultScreenParamsByDebugName(name: String): ScreenParams? {
        return navigationTree.find { it.nameForLogs == name }?.defaultParams
    }

    internal sealed interface NavigationEvent {
        data class Open(val screenParams: ScreenParams) : NavigationEvent
        data class Close(val screenParams: ScreenParams) : NavigationEvent
    }
}
