package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenComponent

internal class SplashScreenComponentImpl(
    context: ComponentContext,
) : SplashScreenComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(Color.Cyan)) {
            Text("Апка загружается", Modifier.align(Alignment.Center))
        }
    }
}
