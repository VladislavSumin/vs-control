package ru.vs.core.navigation.viewModel

import kotlinx.coroutines.channels.Channel
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.core.navigation.ScreenParams

/**
 * Расширение [ViewModel] с поддержкой навигации внутри вью модели без необходимости писать связку явно.
 * Связывание происходит через [navigationChannel] и [ru.vs.core.navigation.screen.Screen.viewModel], поэтому создавать
 * такую вью модель имеет смысл только через эту специальную функцию.
 */
abstract class NavigationViewModel : ViewModel() {
    internal val navigationChannel: Channel<NavigationEvent> = Channel(capacity = Channel.BUFFERED)

    /**
     * Работает аналогично [ru.vs.core.navigation.navigator.ScreenNavigator.open].
     */
    protected fun open(screenParams: ScreenParams) = send(NavigationEvent.Open(screenParams))

    /**
     * Работает аналогично [ru.vs.core.navigation.navigator.ScreenNavigator.close].
     */
    protected fun close(screenParams: ScreenParams) = send(NavigationEvent.Close(screenParams))

    /**
     * Работает аналогично [ru.vs.core.navigation.navigator.ScreenNavigator.close].
     */
    protected fun close() = send(NavigationEvent.CloseSelf)

    private fun send(event: NavigationEvent) {
        navigationChannel.trySend(event).getOrThrow()
    }

    internal sealed interface NavigationEvent {
        data class Open(val screenParams: ScreenParams) : NavigationEvent
        data class Close(val screenParams: ScreenParams) : NavigationEvent
        data object CloseSelf : NavigationEvent
    }
}
