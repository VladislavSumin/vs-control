package ru.vs.core.decompose

import com.arkivanov.decompose.errorhandler.onDecomposeError
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.destroy
import kotlin.test.assertNotEquals

abstract class BaseComponentTest {
    init {
        onDecomposeError = {}
    }

    protected var context = TestComponentContext()
        private set

    /**
     * Пересоздает [context]
     * @param saveState эмитировать сохранение состояния
     * @param saveInstances эмитировать сохранение инстансов.
     */
    fun recreateContext(
        saveState: Boolean = true,
        saveInstances: Boolean = false,
    ) {
        assertNotEquals(Lifecycle.State.DESTROYED, context.lifecycle.state)

        val savedState = if (saveState) {
            context.stateKeeperDispatcher.save()
        } else {
            null
        }

        val instanceKeeperDispatcher = if (saveInstances) {
            context.instanceKeeperDispatcher
        } else {
            InstanceKeeperDispatcher()
        }

        context.lifecycleRegistry.destroy()

        context = TestComponentContext(
            instanceKeeperDispatcher = instanceKeeperDispatcher,
            savedState = savedState,
        )
    }
}
