package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import kotlinx.coroutines.flow.flow
import org.kodein.di.DirectDI
import org.kodein.di.instance
import ru.vs.control.feature.initialization.domain.InitializationInteractor
import ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen.InitializedRootScreenFactory
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
    private var initializedDi: DirectDI? = null

    val state = flow {
        initializedDi = initializationInteractor.init()
        emit(RootScreenState.Content)
    }.stateIn(RootScreenState.Splash)

    fun getContentScreenFactory(): InitializedRootScreenFactory {
        return initializedDi!!.instance()
    }
}
