package ru.vs.core.decompose.router

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.value.Value

/**
 * Преобразует [Value] в [NavigationSource].
 */
internal fun <T : Any> Value<T>.asNavigationSource(): NavigationSource<T> {
    return ValueNavigationSource(this)
}

private class ValueNavigationSource<T : Any>(private val parent: Value<T>) : NavigationSource<T> {
    override fun subscribe(observer: (T) -> Unit): Cancellation {
        return parent.subscribe(observer)
    }
}
