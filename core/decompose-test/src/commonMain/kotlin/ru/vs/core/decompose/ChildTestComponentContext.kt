package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.statekeeper.StateKeeper
import kotlinx.serialization.builtins.serializer

private var counter = 0

/**
 * Обертка для тестирования дочерних элементов Decompose
 * @param data любые данные, необходимые для теста.
 */
class ChildTestComponentContext<T : Any>(
    val data: T,
    context: ComponentContext,
) : ComponentContext by context {
    /**
     * Уникальное число для проверки работы [InstanceKeeper]
     */
    val keepInstanceUniqueValue = instanceKeeper.getOrCreate { RandomValue() }.value

    /**
     * Уникальное числа для проверки работы [StateKeeper]
     */
    val keepStateUniqueValue = stateKeeper.consume(UNIQUE_VALUE_KEY, Int.serializer()) ?: counter++

    init {
        stateKeeper.register(UNIQUE_VALUE_KEY, Int.serializer()) { keepStateUniqueValue }
    }

    private class RandomValue : InstanceKeeper.Instance {
        val value = counter++
        override fun onDestroy() {
            // no action
        }
    }

    companion object {
        private const val UNIQUE_VALUE_KEY = "unique_value"
    }
}
