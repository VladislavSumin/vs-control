package ru.vs.core.navigation.screen

import ru.vs.core.decompose.Component
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.ViewModel
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
