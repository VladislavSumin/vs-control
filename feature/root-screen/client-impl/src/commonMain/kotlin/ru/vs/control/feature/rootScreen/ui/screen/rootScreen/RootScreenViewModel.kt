package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

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
    init {
        launch { initializationInteractor.init() }
    }
}
