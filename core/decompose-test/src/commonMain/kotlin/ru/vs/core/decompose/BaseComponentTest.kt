package ru.vs.core.decompose

import com.arkivanov.decompose.errorhandler.onDecomposeError
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlin.test.assertNotEquals

/**
 * Базовая реализация для тестов проверяющих компоненты Decompose.
 */
abstract class BaseComponentTest {
    init {
        // В тестах повсеместно элементы decompose вызываются с тестового (не main) потока. Поэтому для тестов
        // глушим все ошибки decompose.
        onDecomposeError = {}
    }

    protected var context = TestComponentContext()
        private set

    /**
     * Пересоздает контекст указанным образом.
     * @see RecreateContextType
     */
    protected fun recreateContext(recreateContextType: RecreateContextType = RecreateContextType.Full) {
        assertNotEquals(Lifecycle.State.DESTROYED, context.lifecycle.state)

        val stateKeeperDispatcher = when (recreateContextType) {
            RecreateContextType.Full -> StateKeeperDispatcher()
            RecreateContextType.ProcessDeath,
            RecreateContextType.ConfigurationChange,
            -> {
                val savedState = context.stateKeeperDispatcher.save()
                StateKeeperDispatcher(savedState)
            }
        }

        val instanceKeeperDispatcher = when (recreateContextType) {
            RecreateContextType.Full,
            RecreateContextType.ProcessDeath,
            -> {
                context.instanceKeeperDispatcher.destroy()
                InstanceKeeperDispatcher()
            }

            RecreateContextType.ConfigurationChange -> {
                context.instanceKeeperDispatcher
            }
        }

        context.lifecycleRegistry.destroy()

        context = TestComponentContext(
            stateKeeperDispatcher = stateKeeperDispatcher,
            instanceKeeperDispatcher = instanceKeeperDispatcher,
        )
    }

    /**
     * Описывает типовые сценарии пересоздания контекста.
     *
     * @property Full контекст пересоздается полностью, никакие данные не сохраняются. Этот сценарий эквивалентен
     * полному закрытию и повторному открытию приложения.
     *
     * @property ProcessDeath контекст пересоздается частично:
     *
     *      1. StateKeeper восстанавливается.
     *      2. InstanceKeeper не восстанавливается.
     *
     * Этот кейс эквивалентен запуску приложения после его уничтожения системой с восстановлением сохраненного
     * состояния. Так же это похоже на поведение Android при включенном флаге "Don`t keep activities", так как в этом
     * случае как раз вью модели тоже не переживают сворачивания приложения.
     *
     * @property ConfigurationChange контекст пересоздается с полным восстановлением:
     *
     *       1. StateKeeper восстанавливается.
     *       2. InstanceKeeper восстанавливается.
     *
     *  Этот кейс эквивалентен пересозданию Activity системой Android при смене ориентации экрана или других изменениях
     *  конфигурации которые явно не обрабатываются.
     */
    enum class RecreateContextType {
        Full,
        ProcessDeath,
        ConfigurationChange,
    }
}
