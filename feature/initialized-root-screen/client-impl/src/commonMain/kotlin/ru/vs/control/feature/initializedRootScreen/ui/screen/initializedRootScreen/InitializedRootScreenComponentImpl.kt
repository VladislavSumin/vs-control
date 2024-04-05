package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.navigation.host.childNavigationRoot
import ru.vs.core.navigation.tree.NavigationTree

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponentImpl(
    navigationTree: NavigationTree,
    onContentReady: () -> Unit,
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {

    private val rootNavigation = childNavigationRoot(navigationTree)

    init {
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
