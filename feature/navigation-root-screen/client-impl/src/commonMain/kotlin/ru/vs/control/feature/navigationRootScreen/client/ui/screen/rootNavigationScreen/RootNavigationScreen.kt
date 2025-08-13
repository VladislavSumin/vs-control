package ru.vs.control.feature.navigationRootScreen.client.ui.screen.rootNavigationScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.host.childNavigationSlot
import ru.vs.control.feature.navigationRootScreen.client.ui.screen.RootNavigationHost
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class RootNavigationScreen(
    viewModelFactory: RootNavigationScreenViewModelFactory,
    context: VsComponentContext,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val delaySplash = Mutex(true)

    private val childSlotNavigation: Value<ChildSlot<*, ComposeComponent>> = childNavigationSlot(
        initialConfiguration = { null },
        navigationHost = RootNavigationHost,
    )

    init {
        selectInitialScreen()
    }

    /**
     * Выбирает стартовый экран, а так же
     */
    private fun selectInitialScreen() = launch {
        // TODO починить работу диплинков вместе с этим методом!
        // -W -a android.intent.action.VIEW -d "vs-control://MainScreenParams" ru.vs.control
        // При пересоздании компонента или при восстановлении сохраненного состояние экран может быть уже установлен
        if (childSlotNavigation.value.child == null) {
            val config = viewModel.getInitialConfiguration()
            navigator.open(config)
        }
        delaySplash.unlock()
    }

    override suspend fun delaySplashScreen() {
        // В данном случае нормально рассчитывать на splash экран и не добавлять дополнительный loader, так как этот
        // экран всегда открывается из корня, а следовательно для него всегда должен вызываться этот метод
        delaySplash.withLock {}
    }

    @Composable
    override fun Render(modifier: Modifier) {
        val child = childSlotNavigation.subscribeAsState().value.child
        child?.instance?.Render(modifier)
    }
}
