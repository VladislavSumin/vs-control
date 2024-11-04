package ru.vs.core.navigation.screen

import com.arkivanov.essenty.instancekeeper.getOrCreate
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
     * Создает или возвращает созданную ранее [ViewModel] используя для этого [instanceKeeper].
     * Так же если [T] является наследником [NavigationViewModel], то связывает навигацию экрана с навигацией ViewModel.
     */
    protected inline fun <reified T : ViewModel> viewModel(factory: () -> T): T {
        val viewModel = instanceKeeper.getOrCreate { factory() }
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
