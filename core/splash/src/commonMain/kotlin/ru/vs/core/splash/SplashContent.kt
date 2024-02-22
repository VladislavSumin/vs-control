package ru.vs.core.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.Value

@Composable
fun <T : Any> Children(
    stack: Value<ChildSplash<T>>,
    modifier: Modifier = Modifier,
//    animation: StackAnimation<C, T>? = null,
    content: @Composable (T) -> Unit,
) {
    val state by stack.subscribeAsState()
    Children(state, modifier, content)
}

@Composable
private fun <T : Any> Children(
    stack: ChildSplash<T>,
    modifier: Modifier = Modifier,
//    animation: StackAnimation<C, T>? = null,
    content: @Composable (T) -> Unit,
) {
    val current = stack.current
    // TODO коробка нужна что бы потом заменить ее анимацией.
    Box(modifier) {
        // Оригинально Аркадий использует сложную систему для правильного сохранения состояние композиции
        // при навигации. Но с splash навигацией почему-то состояние восстанавливается и так. Если в будущем
        // сломается нужно добавить полноценное сохранение состояния через SaveableStateHolder.
        content(current)
    }
}
