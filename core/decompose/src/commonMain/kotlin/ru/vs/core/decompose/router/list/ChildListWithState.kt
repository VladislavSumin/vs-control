package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Creates list with any items has our own lifecycle and state
 * For different [C] with equal [ID] keep created [T], reuse it and update it state to new [C]
 *
 * @param C - item state type
 * @param ID - unique id type
 * @param T - resulting component type
 *
 * @param idSelector - get **unique** id for any source list element
 *
 * TODO write performance tests to it
 */
fun <C : Any, ID : Any, T : ComponentContext> ComponentContext.childListWithState(
    state: Value<List<C>>,
    idSelector: (C) -> ID,
    key: String = "DefaultChildList",
    childFactory: (state: StateFlow<C>, ComponentContext) -> T,
): Value<List<T>> {
    val cache = mutableMapOf<ID, MutableStateFlow<C>>()

    fun updateCache(configurations: List<C>) {
        configurations.forEach { configuration ->
            cache.getOrPut(idSelector(configuration)) { MutableStateFlow(configuration) }.value = configuration
        }
    }

    val idState = state.map {
        updateCache(it)
        it.map(idSelector)
    }

    return childList(
        state = idState,
        key = key,
        childFactory = { id, context ->
            val stateFlow = cache[id]!!
            val childComponent = childFactory(stateFlow, context)
            childComponent.lifecycle.doOnDestroy { cache.remove(id) }
            childComponent
        },
    )
}
