package ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.vs.control.feature.mainScreen.ui.screen.mainScreen.MainScreenParams
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.host.childNavigationStack
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class RootContentScreen(context: ScreenContext) : Screen(context) {
    private val childStack = childNavigationStack(
        navigationHost = RootContentNavigationHost,
        defaultStack = { listOf(MainScreenParams) },
        handleBackButton = true,
    )

    @Composable
    override fun Render(modifier: Modifier) {
        Children(
            childStack,
            modifier,
            stackAnimation(fade() + scale()),
        ) {
            it.instance.Render(Modifier.fillMaxSize())
        }
    }
}
