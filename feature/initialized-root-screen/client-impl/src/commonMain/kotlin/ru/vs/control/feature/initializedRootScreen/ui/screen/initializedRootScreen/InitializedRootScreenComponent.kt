package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import ru.vs.core.decompose.Component
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.navigation.host.childNavigationRoot

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponent(
    viewModelFactory: InitializedRootViewModelFactory,
    onContentReady: () -> Unit,
    deeplink: ReceiveChannel<String>,
    context: ComponentContext,
) : Component(context), ComposeComponent {

    private val viewModel = viewModel { viewModelFactory.create() }
    private val rootNavigation = childNavigationRoot(viewModel.navigation)

    init {
        launch { deeplink.consumeEach(viewModel::onDeeplink) }

        // TODO передавать onContentReady() в навигацию.
        onContentReady()
    }

    @Composable
    override fun Render(modifier: Modifier) {
        rootNavigation.Render(modifier)
    }
}
