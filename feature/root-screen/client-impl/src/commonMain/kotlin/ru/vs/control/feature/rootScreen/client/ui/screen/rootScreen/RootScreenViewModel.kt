package ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen

import kotlinx.coroutines.flow.flow
import org.kodein.di.DirectDI
import org.kodein.di.instance
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.initialization.client.domain.InitializationInteractor
import ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen.InitializedRootScreenFactory

@GenerateFactory
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
