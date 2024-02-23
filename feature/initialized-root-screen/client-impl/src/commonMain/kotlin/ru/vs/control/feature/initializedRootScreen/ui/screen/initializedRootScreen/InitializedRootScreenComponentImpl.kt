package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.navigation.NavigationGraph
import kotlin.random.Random

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
internal class InitializedRootScreenComponentImpl(
    // TODO пока не используется, на будущее.
    @Suppress("UnusedPrivateProperty")
    private val navigationGraph: NavigationGraph,
    onContentReady: () -> Unit,
    context: ComponentContext,
) : InitializedRootScreenComponent, ComponentContext by context {

    @Suppress("MagicNumber")
    private val rand = stateKeeper.consume("rand", Int.serializer()) ?: Random.nextInt(1_000_000)

    init {
        stateKeeper.register("rand", Int.serializer()) { rand }
        val scope = lifecycle.createCoroutineScope()
        scope.launch {
            @Suppress("MagicNumber")
            delay(1500)
            onContentReady()
        }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        @Suppress("MagicNumber")
        val rand2 by rememberSaveable { mutableStateOf(Random.nextInt(1_000_000)) }
        Box(modifier.background(Color.Yellow)) {
            Column(Modifier.align(Alignment.Center)) {
                Text("Initialized root screen")
                Text("component state: $rand")
                Text("compose state: $rand2")
            }
        }
    }
}
