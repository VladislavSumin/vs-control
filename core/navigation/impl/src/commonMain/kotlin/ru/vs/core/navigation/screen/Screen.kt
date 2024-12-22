package ru.vs.core.navigation.screen

import ru.vladislavsumin.core.decompose.components.Component
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.viewModel.NavigationViewModel

/**
 * Базовая реализация экрана с набором полезных расширений.
 */
abstract class Screen(context: ScreenContext) :
    Component(context),
    ComposeComponent,
    ScreenContext,
    BaseScreenContext by context {

    /**
     * Предоставляет искусственно задержать splash экран на время загрузки контента вашего экрана.
     *
     * Эта функция может быть вызвана при холодном старте, если экран открывается сразу или при восстановлении
     * состояния после смерти процесса.
     *
     * Эта функция *не* будет вызвана при обычном переходе на экран, если splash экран уже был закрыт.
     */
    protected open suspend fun delaySplashScreen() {}
    internal suspend fun delaySplashScreenInternal() = delaySplashScreen()

    /**
     * Расширяет стандартную [Component.viewModel] добавляя дополнительный функционал навигации:
     * Если [T] является наследником [NavigationViewModel], то связывает навигацию экрана с навигацией ViewModel.
     */
    final override fun <T : ViewModel> viewModel(factory: () -> T): T {
        val viewModel = super.viewModel(factory)
        (viewModel as? NavigationViewModel)?.handleNavigation()
        return viewModel
    }

    @PublishedApi
    internal fun NavigationViewModel.handleNavigation() = launch {
        for (event in navigationChannel) {
            when (event) {
                is NavigationViewModel.NavigationEvent.Open -> navigator.open(event.screenParams)
                is NavigationViewModel.NavigationEvent.Close -> navigator.close(event.screenParams)
                NavigationViewModel.NavigationEvent.CloseSelf -> navigator.close()
            }
        }
    }
}
