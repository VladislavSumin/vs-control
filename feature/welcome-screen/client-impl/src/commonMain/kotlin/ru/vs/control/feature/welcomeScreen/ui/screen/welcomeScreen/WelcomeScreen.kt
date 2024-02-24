package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.vs.navigation.Screen
import ru.vs.navigation.ScreenContext
import ru.vs.navigation.ScreenFactory

internal class WelcomeScreenFactory : ScreenFactory<WelcomeScreenParams, WelcomeScreen> {
    override fun create(context: ScreenContext, params: WelcomeScreenParams): WelcomeScreen {
        return WelcomeScreen(context)
    }
}

internal class WelcomeScreen(
    context: ScreenContext,
) : Screen, ScreenContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(Color.Cyan)) {
            Text("WelcomeScreen", Modifier.align(Alignment.Center))
        }
    }
}
