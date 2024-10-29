package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.statekeeper.SerializableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

class TestComponentContext internal constructor(
    componentContext: ComponentContext,
    lifecycleRegistry: LifecycleRegistry,
    stateKeeperDispatcher: StateKeeperDispatcher,
) : ComponentContext by componentContext,
    LifecycleRegistry by lifecycleRegistry,
    StateKeeperDispatcher by stateKeeperDispatcher

/**
 * Создает удобный тестовый [ComponentContext], где все функции управления вынесены наружу.
 */
fun TestComponentContext(
    savedState: SerializableContainer? = null,
): TestComponentContext {
    val stateKeeperDispatcher = StateKeeperDispatcher(savedState)
    val lifecycleRegistry = LifecycleRegistry()
    val context = DefaultComponentContext(
        lifecycleRegistry,
        stateKeeperDispatcher,
    )
    return TestComponentContext(
        context,
        lifecycleRegistry,
        stateKeeperDispatcher,
    )
}

fun ResumedTestComponentContext(
    savedState: SerializableContainer? = null,
): TestComponentContext {
    val context = TestComponentContext(savedState)
    context.resume()
    return context
}
