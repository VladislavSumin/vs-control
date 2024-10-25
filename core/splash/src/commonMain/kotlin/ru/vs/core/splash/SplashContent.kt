package ru.vs.core.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.Value

/**
 * Контейнер для отображения [childSplash] навигации.
 * @param stack состояние навигации.
 * @param modifier этого контейнера.
 * @param splashOutAnimation анимация с которой будет пропадать Splash экран. Тут пока не стал усложнять, кажется такой
 * анимации хватит, если перестанет хватать, то всегда можно переписать этот момент.
 * @param content контент навигации (Splash/Content) экраны.
 */
@Composable
fun <T : Any> Children(
    stack: Value<ChildSplash<T>>,
    modifier: Modifier = Modifier,
    splashOutAnimation: ExitTransition = scaleOut(targetScale = 1.3f) + fadeOut(),
    content: @Composable (T) -> Unit,
) {
    val state by stack.subscribeAsState()
    Children(state, modifier, splashOutAnimation, content)
}

@Composable
private fun <T : Any> Children(
    stack: ChildSplash<T>,
    modifier: Modifier = Modifier,
    contentOutAnimation: ExitTransition,
    content: @Composable (T) -> Unit,
) {
    val child = stack.child

    // Сохраняет "текущее" состояние навигации (при изменении stack в аргументе, это состояние будет предыдущим).
    var previousState by remember { mutableStateOf(child) }

    // Сохраняет текущую стопку детей. В ней Content (если присутствует), то всегда находится первым, так как должен
    // рисоваться под Splash в момент анимации.
    val currentChildrenList by remember { mutableStateOf(mutableListOf(child)) }

    if (previousState != child) {
        check(child.configuration == ChildSplashConfiguration.Content) { "Allowed only Splash to Content navigation" }
        previousState = child
        // Content добавляем в начало списка чтобы он был нарисован под splash экраном
        currentChildrenList.add(0, child)
    }

    Box(modifier) {
        currentChildrenList.forEach {
            when (it.configuration) {
                ChildSplashConfiguration.Splash -> {
                    // Splash показываем с анимацией.
                    AnimatedVisibility(
                        visible = child.configuration == ChildSplashConfiguration.Splash,
                        exit = contentOutAnimation,
                    ) {
                        content(it.instance)

                        // При exit анимации после того как компонент перестанет быть виден его композиция будет
                        // уничтожена, тогда мы должны удалить эту конфигурацию из памяти.
                        DisposableEffect(Unit) {
                            onDispose {
                                // Splash всегда лежит вторым элементом в листе.
                                currentChildrenList.removeAt(1)
                            }
                        }
                    }
                }

                ChildSplashConfiguration.Content -> {
                    // Оригинально Аркадий использует сложную систему для правильного сохранения состояния композиции
                    // при навигации. Но с splash навигацией почему-то состояние восстанавливается и так. Если в будущем
                    // сломается нужно добавить полноценное сохранение состояния через SaveableStateHolder.

                    // Предположительно это происходит так как мы сохраняем состояние контента при смерти процесса, при
                    // этом при восстановлении мы сначала показываем Splash. Похоже, что Compose ожидает получить то же
                    // состояние, что и раньше и потому сохраняет состояние контента до его фактического появления на
                    // экране.
                    content(it.instance)
                }
            }
        }
    }
}
