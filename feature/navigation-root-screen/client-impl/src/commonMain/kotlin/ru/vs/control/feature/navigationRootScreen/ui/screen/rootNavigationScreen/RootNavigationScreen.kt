package ru.vs.control.feature.navigationRootScreen.ui.screen.rootNavigationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.vs.navigation.Screen
import ru.vs.navigation.ScreenContext
import ru.vs.navigation.ScreenFactory

internal class RootNavigationScreenFactory : ScreenFactory<RootNavigationScreenParams, RootNavigationScreen> {
    override fun create(context: ScreenContext, params: RootNavigationScreenParams): RootNavigationScreen {
        return RootNavigationScreen(context)
    }
}

internal class RootNavigationScreen(context: ScreenContext) : Screen, ScreenContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(Color.Green)) {
            Text("RootNavigationScreen")
        }
    }
}
