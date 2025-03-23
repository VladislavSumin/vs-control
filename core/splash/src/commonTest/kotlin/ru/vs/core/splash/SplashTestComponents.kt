package ru.vs.core.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.arkivanov.decompose.ComponentContext
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import kotlin.random.Random

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
        Column(modifier.testTag(TAG)) {
            Text("Content screen")

            val data = rememberSaveable { Random.nextInt() }
            Text(data.toString(), Modifier.testTag(SAVEABLE_CONTENT_TAG))
        }
    }

    companion object {
        const val TAG = "content_screen"
        const val SAVEABLE_CONTENT_TAG = "saveable_content"
    }
}
