package ru.vs.control.feature.rootContentScreen.ui.screen.rootContentScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenParams
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.host.childNavigationStack
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class RootContentScreen(context: ScreenContext) : Screen(context) {
    private val childStack = childNavigationStack(
        navigationHost = RootContentNavigationHost,
        initialStack = { listOf(MainScreenParams) },
        handleBackButton = true,
    )

    @Composable
    override fun Render(modifier: Modifier) {
        Children(childStack, modifier) {
            it.instance.Render(Modifier.fillMaxSize())
        }
    }
}
