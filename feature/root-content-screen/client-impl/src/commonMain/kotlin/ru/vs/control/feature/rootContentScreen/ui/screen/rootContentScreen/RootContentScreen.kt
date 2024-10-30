package ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.vs.control.feature.debugScreen.ui.screen.debugScreen.DebugScreenParams
import ru.vs.core.navigation.host.childNavigationStack
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class RootContentScreenFactory : ScreenFactory<RootContentScreenParams, RootContentScreen> {
    override fun create(context: ScreenContext, params: RootContentScreenParams): RootContentScreen {
        return RootContentScreen(context)
    }
}

internal class RootContentScreen(context: ScreenContext) : Screen(context) {
    private val childStack = childNavigationStack(
        navigationHost = RootContentNavigationHost,
        initialStack = { listOf(DebugScreenParams) },
    )

    @Composable
    override fun Render(modifier: Modifier) {
        Children(childStack, modifier) {
            it.instance.Render(Modifier.fillMaxSize())
        }
    }
}
