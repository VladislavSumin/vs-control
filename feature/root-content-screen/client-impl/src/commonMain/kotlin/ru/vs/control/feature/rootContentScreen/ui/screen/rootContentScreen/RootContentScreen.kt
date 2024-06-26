package ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class RootContentScreenFactory : ScreenFactory<RootContentScreenParams, RootContentScreen> {
    override fun create(context: ScreenContext, params: RootContentScreenParams): RootContentScreen {
        return RootContentScreen(context)
    }
}

internal class RootContentScreen(
    context: ScreenContext,
) : Screen, ScreenContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.systemBarsPadding()) {
            Text("RootContentScreen", Modifier.align(Alignment.Center))
            Button({ navigator.open(DebugScreenParams) }) {
                Text("open debug")
            }
        }
    }
}
