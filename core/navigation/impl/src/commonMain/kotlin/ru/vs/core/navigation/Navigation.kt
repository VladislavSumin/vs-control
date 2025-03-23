package ru.vs.core.navigation

import kotlinx.coroutines.channels.Channel
import ru.vladislavsumin.core.collections.tree.asSequence
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

    /**
     * Ищет параметры экрана по их имени. Можно использовать для реализации отладочных ссылок.
     * **Внимание** Названия параметров могут быть изменены при минимизации приложения, поэтому данный метод не будет
     * работать в релизе.
     */
    fun findDefaultScreenParamsByName(name: String): ScreenParams? {
        return navigationTree
            .asSequence()
            .find { it.value.screenKey.key.simpleName == name }
            ?.value
            ?.defaultParams
    }

    internal sealed interface NavigationEvent {
        data class Open(val screenParams: ScreenParams) : NavigationEvent
        data class Close(val screenParams: ScreenParams) : NavigationEvent
    }
}
