package ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.host.childNavigationSlot
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class RootNavigationScreen(
    viewModelFactory: RootNavigationScreenViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val childSlotNavigation: Value<ChildSlot<ScreenParams, Screen>> = childNavigationSlot(
        initialConfiguration = { null },
        navigationHost = RootNavigationHost,
    )

    init {
        launch {
            // TODO тут асинхронщина при отображении экрана.
            // TODO тут при пересоздании экрана будет не нужная навигация, так же при восстановлении состояния
            val config = viewModel.getInitialConfiguration()
            navigator.open(config)
        }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        val child = childSlotNavigation.subscribeAsState().value.child
        child?.instance?.Render(modifier)
    }
}
