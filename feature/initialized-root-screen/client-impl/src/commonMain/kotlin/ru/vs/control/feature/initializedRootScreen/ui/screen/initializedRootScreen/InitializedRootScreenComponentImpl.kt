package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.navigation.host.childNavigationRoot

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponentImpl(
    viewModelFactory: InitializedRootViewModelFactory,
    onContentReady: () -> Unit,
    deeplink: ReceiveChannel<String>,
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {

    private val viewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }
    private val scope = lifecycle.createCoroutineScope()
    private val rootNavigation = childNavigationRoot(viewModel.navigation)

    init {
        scope.launch { deeplink.consumeEach(viewModel::onDeeplink) }

        // TODO временный код для эмитации долгой загрузки.
        // TODO передавать эту лябду в навигацию.
        scope.launch {
            @Suppress("MagicNumber")
            delay(500)
            onContentReady()
        }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        rootNavigation.Render(modifier)
    }
}
