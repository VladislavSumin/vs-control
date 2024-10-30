package ru.vs.core.navigation.viewModel

import kotlinx.coroutines.channels.Channel
import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.ScreenParams

/**
 * Расширение [ViewModel] с поддержкой навигации внутри вью модели без необходимости писать связку явно.
 * Связывание происходит через [navigationChannel] и [ru.vs.core.navigation.screen.Screen.viewModel], поэтому создавать
 * такую вью модель имеет смысл только через эту специальную функцию.
 */
abstract class NavigationViewModel : ViewModel() {
    internal val navigationChannel: Channel<ScreenParams> = Channel(capacity = Channel.BUFFERED)

    /**
     * Работает аналогично [ru.vs.core.navigation.navigator.ScreenNavigator.open].
     */
    protected fun open(screenParams: ScreenParams) {
        navigationChannel.trySend(screenParams).getOrThrow()
    }
}
