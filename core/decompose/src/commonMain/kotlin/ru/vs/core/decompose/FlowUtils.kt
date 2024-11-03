package ru.vs.core.decompose

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Преобразует [StateFlow] в [Value].
 */
fun <T : Any> StateFlow<T>.asValue(scope: CoroutineScope): Value<T> {
    return FlowAsValue(this, scope)
}

private class FlowAsValue<T : Any>(
    private val parentFlow: StateFlow<T>,
    private val scope: CoroutineScope,
) : Value<T>() {
    override val value: T get() = parentFlow.value
    override fun subscribe(observer: (T) -> Unit): Cancellation {
        val job = scope.launch { parentFlow.collect(observer) }
        return Cancellation { job.cancel() }
    }
}
