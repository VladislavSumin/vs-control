package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

    @Composable
    override fun Render(modifier: Modifier) {
        // TODO Временный код
        val state by viewModel.state.collectAsState()
        when (state) {
            RootScreenState.Splash -> {
                splashScreenComponent.Render(modifier.background(Color.Cyan))
            }

            RootScreenState.Content -> {
                Box(modifier.background(Color.Gray)) {
                    Text("Aпка загрузилась", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
