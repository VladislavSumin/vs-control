package ru.vs.core.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.Value

/**
 * Контейнер для отображения [childSplash] навигации.
 * @param stack состояние навигации.
 * @param modifier этого контейнера.
 * @param splashExitTransition анимация закрытия splash screen.
 * @param contentEnterTransition анимация открытия content screen.
 * @param content контент навигации (Splash/Content) экраны.
 */
@Composable
fun <T : Any> Children(
    stack: Value<ChildSplash<T>>,
    modifier: Modifier = Modifier,
    splashExitTransition: ExitTransition = scaleOut(targetScale = 1.3f) + fadeOut(),
    contentEnterTransition: EnterTransition = EnterTransition.None,
    content: @Composable (T) -> Unit,
) {
    val state by stack.subscribeAsState()
    Children(
        stack = state,
        modifier = modifier,
        transitionSpec = {
            // В данном случае у нас может быть только один вид перехода Splash -> Content,
            // поэтому можем не учитывать параметры и всегда создавать только одну анимацию
            contentEnterTransition.togetherWith(splashExitTransition)
        },
        content = content,
    )
}

@Composable
private fun <T : Any> Children(
    stack: ChildSplash<T>,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<*>.() -> ContentTransform,
    content: @Composable (T) -> Unit,
) {
    val child = stack.child

    // Костыль для правильного сохранения и восстановления состояния. Если не использовать movableContentOf, то
    // состояние хоть и восстанавливается корректно, но едут хеши которые compose при отсутствии явного ключа.
    // Использование SaveableStateHolder не исправляет эту проблему.
    val savedContent = remember { movableContentOf<T> { content(it) } }

    AnimatedContent(
        child,
        modifier,
        transitionSpec = transitionSpec,
    ) { child ->
        when (child.configuration) {
            ChildSplashConfiguration.Splash -> {
                content(child.instance)
            }

            ChildSplashConfiguration.Content -> {
                savedContent(child.instance)
            }
        }
    }
}
