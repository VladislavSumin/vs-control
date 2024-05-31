package ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.host.childNavigationSlot
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class RootNavigationScreenFactory(
    private val viewModelFactory: RootNavigationScreenViewModelFactory,
) : ScreenFactory<RootNavigationScreenParams, RootNavigationScreen> {
    override fun create(context: ScreenContext, params: RootNavigationScreenParams): RootNavigationScreen {
        return RootNavigationScreen(viewModelFactory, context)
    }
}

internal class RootNavigationScreen(
    viewModelFactory: RootNavigationScreenViewModelFactory,
    context: ScreenContext,
) : Screen, ScreenContext by context {
    private val viewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }

    private val childSlotNavigation: Value<ChildSlot<ScreenParams, Screen>> = childNavigationSlot(
        initialConfiguration = viewModel::getInitialConfiguration,
        navigationHost = RootNavigationHost,
        key = "RootNavigationHost",
    )

    @Composable
    override fun Render(modifier: Modifier) {
        val child = childSlotNavigation.subscribeAsState().value.child
        child?.instance?.Render(modifier)
    }
}
