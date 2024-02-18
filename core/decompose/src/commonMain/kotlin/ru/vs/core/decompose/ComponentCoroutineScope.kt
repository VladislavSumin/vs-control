package ru.vs.core.decompose

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/**
 * Создает [CoroutineScope] связанный с [Lifecycle]. [CoroutineScope] будет автоматически закрыт
 * при переходе [Lifecycle] в состояние [Lifecycle.State.DESTROYED].
 */
fun Lifecycle.createCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate) =
    ComponentCoroutineScope(context, this)

private fun ComponentCoroutineScope(
    context: CoroutineContext,
    lifecycle: Lifecycle,
): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}
