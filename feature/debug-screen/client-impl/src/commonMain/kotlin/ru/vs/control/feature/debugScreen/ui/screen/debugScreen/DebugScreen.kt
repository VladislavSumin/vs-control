package ru.vs.control.feature.debugScreen.ui.screen.debugScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory

internal class DebugScreenFactory(
    private val viewModelFactory: DebugViewModelFactory,
    private val umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
) : ScreenFactory<DebugScreenParams, DebugScreen> {
    override fun create(context: ScreenContext, params: DebugScreenParams): DebugScreen {
        return DebugScreen(umlDiagramComponentFactory, viewModelFactory, context)
    }
}

internal class DebugScreen(
    umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
    viewModelFactory: DebugViewModelFactory,
    context: ScreenContext,
) : Screen(context) {

    val viewModel = viewModel { viewModelFactory.create() }

    private val umlDiagramComponent = umlDiagramComponentFactory.create(
        childContext("uml"),
        navigationTreeInterceptor = { viewModel.generateFakeNavigationNodesFist(it) },
    )

    @Composable
    override fun Render(modifier: Modifier) = DebugContent(viewModel, umlDiagramComponent, modifier)
}
