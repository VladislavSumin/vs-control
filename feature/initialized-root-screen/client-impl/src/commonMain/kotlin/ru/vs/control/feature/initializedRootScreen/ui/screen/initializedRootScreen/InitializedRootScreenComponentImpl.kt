package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.navigation.Navigation
import ru.vs.core.navigation.host.childNavigationRoot

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponentImpl(
    navigation: Navigation,
    onContentReady: () -> Unit,
    // TODO добавить дальнейшую обработку.
    deeplink: ReceiveChannel<String>,
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {

    private val rootNavigation = childNavigationRoot(navigation)

    init {
        // TODO временный код для эмитации долгой загрузки.
        // TODO передавать эту лябду в навигацию.
        val scope = lifecycle.createCoroutineScope()
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
