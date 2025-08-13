package ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.host.childNavigationStack
import ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen.MainScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class RootContentScreen(context: VsComponentContext) : VsScreen(context) {
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
