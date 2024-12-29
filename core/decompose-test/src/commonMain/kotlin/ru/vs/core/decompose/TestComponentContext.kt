package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.SerializableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

interface TestComponentContext : ComponentContext {
    val lifecycleRegistry: LifecycleRegistry
    val stateKeeperDispatcher: StateKeeperDispatcher
    val instanceKeeperDispatcher: InstanceKeeperDispatcher
}

private class TestComponentContextImpl(
    componentContext: ComponentContext,
    override val lifecycleRegistry: LifecycleRegistry,
    override val stateKeeperDispatcher: StateKeeperDispatcher,
    override val instanceKeeperDispatcher: InstanceKeeperDispatcher,
) : TestComponentContext, ComponentContext by componentContext

/**
 * Создает удобный тестовый [ComponentContext], где все функции управления вынесены наружу.
 */
fun TestComponentContext(
    lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(),
    instanceKeeperDispatcher: InstanceKeeperDispatcher = InstanceKeeperDispatcher(),
    savedState: SerializableContainer? = null,
): TestComponentContext {
    val stateKeeperDispatcher = StateKeeperDispatcher(savedState)
    val context = DefaultComponentContext(
        lifecycleRegistry,
        stateKeeperDispatcher,
        instanceKeeperDispatcher,
    )
    return TestComponentContextImpl(
        context,
        lifecycleRegistry,
        stateKeeperDispatcher,
        instanceKeeperDispatcher,
    )
}
