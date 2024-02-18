package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vs.core.decompose.createCoroutineScope

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponentImpl(
    onContentReady: () -> Unit,
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {

    init {
        val scope = lifecycle.createCoroutineScope()
        scope.launch {
            @Suppress("MagicNumber")
            delay(3000)
            onContentReady()
        }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier) {
            Text("Initialized root screen", Modifier.align(Alignment.Center))
        }
    }
}
