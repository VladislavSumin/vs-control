package ru.vs.core.sharedElementTransition

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

/**
 * Хранит окружение shared element transition. Для работы с состоянием смотрите функции
 * [ProvideLocalSharedElementTransition] и [WithLocalSharedElementTransition].
 */
private val LocalSharedElementTransition: ProvidableCompositionLocal<TransitionData> =
    compositionLocalOf { error("Local shared element transition not set") }

@OptIn(ExperimentalSharedTransitionApi::class)
private data class TransitionData(
    val sharedTransitionScope: SharedTransitionScope,
    val animatedContentScope: AnimatedContentScope,
)

/**
 * Сохраняет состояние для работы с shared element transition.
 * Сохраненное состояние в дальнейшем можно использовать с помощью [WithLocalSharedElementTransition].
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProvideLocalSharedElementTransition(
    animatedContentScope: AnimatedContentScope,
    content: @Composable () -> Unit,
) {
    val data = remember(this, animatedContentScope) { TransitionData(this, animatedContentScope) }
    CompositionLocalProvider(
        LocalSharedElementTransition provides data,
        content,
    )
}

/**
 * Позволяет использовать окружение для работы с shared element transition сохраненное в
 * [ProvideLocalSharedElementTransition].
 *
 * Типовой пример использования:
 * ```
 * WithLocalSharedElementTransition {
 *     Icon(
 *         <...>,
 *         Modifier.sharedElement(
 *             rememberSharedContentState("key"),
 *             it,
 *         ),
 *     )
 * }
 * ```
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WithLocalSharedElementTransition(
    content: @Composable SharedTransitionScope.(AnimatedContentScope) -> Unit,
) {
    val data = LocalSharedElementTransition.current
    data.sharedTransitionScope.content(data.animatedContentScope)
}
