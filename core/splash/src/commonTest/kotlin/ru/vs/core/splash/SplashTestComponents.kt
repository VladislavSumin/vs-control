package ru.vs.core.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

internal class SplashScreenComponent(
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {

    @Composable
    override fun Render(modifier: Modifier) {
        Text("Splash screen", modifier.testTag(TAG))
    }

    companion object {
        const val TAG = "splash_screen"
    }
}

internal class ContentScreenComponent(
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {

    @Composable
    override fun Render(modifier: Modifier) {
        Text("Content screen", modifier.testTag(TAG))
    }

    companion object {
        const val TAG = "content_screen"
    }
}
