package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import kotlinx.coroutines.flow.flow
import ru.vs.control.feature.initialization.domain.InitializationInteractor
import ru.vs.core.decompose.ViewModel

internal class RootScreenViewModelFactory(
    private val initializationInteractor: InitializationInteractor,
) {
    fun create(): RootScreenViewModel {
        return RootScreenViewModel(initializationInteractor)
    }
}

internal class RootScreenViewModel(
    private val initializationInteractor: InitializationInteractor,
) : ViewModel() {
    val state = flow {
        initializationInteractor.init()
        emit(RootScreenState.Content)
    }.stateIn(RootScreenState.Splash)
}
