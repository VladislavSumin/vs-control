package ru.vs.control.feature.debugScreen.ui.screen.debugScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.childContext
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory

internal class DebugScreenFactory(
    private val umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
) : ScreenFactory<DebugScreenParams, DebugScreen> {
    override fun create(context: ScreenContext, params: DebugScreenParams): DebugScreen {
        return DebugScreen(umlDiagramComponentFactory, context)
    }
}

internal class DebugScreen(
    umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
    context: ScreenContext,
) : Screen, ScreenContext by context {

    private val umlDiagramComponent = umlDiagramComponentFactory.create(childContext("uml"))

    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(Color.Cyan).clipToBounds()) {
            Column {
                Text("DebugScreen")
                umlDiagramComponent.Render(Modifier.fillMaxSize())
            }
        }
    }
}
