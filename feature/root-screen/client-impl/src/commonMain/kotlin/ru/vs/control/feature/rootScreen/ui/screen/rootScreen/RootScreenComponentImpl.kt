package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory

internal class RootScreenComponentImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    splashScreenFactory: SplashScreenFactory,
    context: ComponentContext,
) : RootScreenComponent, ComponentContext by context {
    private val viewModel = instanceKeeper.getOrCreate { rootScreenViewModelFactory.create() }
    private val splashScreenComponent = splashScreenFactory.create(childContext("splash-screen"))

    init {
        // TODO
        viewModel
    }

    @Composable
    override fun Render(modifier: Modifier) {
        splashScreenComponent.Render(modifier.background(Color.Cyan))
    }
}
