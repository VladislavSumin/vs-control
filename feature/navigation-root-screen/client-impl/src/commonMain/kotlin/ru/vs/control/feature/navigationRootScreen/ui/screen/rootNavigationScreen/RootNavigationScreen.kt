package ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen

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
import kotlinx.serialization.builtins.serializer
import ru.vs.navigation.Screen
import ru.vs.navigation.ScreenContext
import ru.vs.navigation.ScreenFactory
import kotlin.random.Random
import ru.vs.control.feature.navigationRootScreen.ui.screen.RootNavigationHost
import ru.vs.core.navigation.host.childNavigationSlot

internal class RootNavigationScreenFactory : ScreenFactory<RootNavigationScreenParams, RootNavigationScreen> {
    override fun create(context: ScreenContext, params: RootNavigationScreenParams): RootNavigationScreen {
        return RootNavigationScreen(context)
    }
}

internal class RootNavigationScreen(context: ScreenContext) : Screen, ScreenContext by context {

    private val childSlotNavigation = childNavigationSlot(
        navigationHost = RootNavigationHost,
    )

    @Suppress("MagicNumber")
    private val rand = stateKeeper.consume("rand", Int.serializer()) ?: Random.nextInt(1_000_000)

    init {
        stateKeeper.register("rand", Int.serializer()) { rand }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        @Suppress("MagicNumber")
        val rand2 by rememberSaveable { mutableStateOf(Random.nextInt(1_000_000)) }
        Box(modifier.background(Color.Yellow)) {
            Column(Modifier.align(Alignment.Center)) {
                Text("RootNavigationScreen")
                Text("component state: $rand")
                Text("compose state: $rand2")
            }
        }
    }
}
