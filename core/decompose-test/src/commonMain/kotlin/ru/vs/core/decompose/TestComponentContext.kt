package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

/**
 * Создает удобный тестовый [ComponentContext], где все функции управления вынесены наружу.
 *
 * Тестовый компонент имеет некоторые нюансы в своей работе, этот компонент **не** уничтожает [InstanceKeeper] при
 * переходе [Lifecycle] в состояние [Lifecycle.State.DESTROYED] как это делает [DefaultComponentContext].
 * В данном случае поведение похоже на Android, где состояние [InstanceKeeper] **нужно очищать вручную**.
 */
class TestComponentContext(
    val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(),
    val stateKeeperDispatcher: StateKeeperDispatcher = StateKeeperDispatcher(),
    val instanceKeeperDispatcher: InstanceKeeperDispatcher = InstanceKeeperDispatcher(),
    val backDispatcher: BackDispatcher = BackDispatcher(),
) : ComponentContext {
    override val lifecycle: Lifecycle = lifecycleRegistry
    override val stateKeeper: StateKeeper = stateKeeperDispatcher
    override val instanceKeeper: InstanceKeeper = instanceKeeperDispatcher
    override val backHandler: BackHandler = backDispatcher
    override val componentContextFactory: ComponentContextFactory<ComponentContext> =
        ComponentContextFactory(::DefaultComponentContext)
}
