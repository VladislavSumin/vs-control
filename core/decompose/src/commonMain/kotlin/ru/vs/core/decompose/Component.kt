package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vs.core.utils.unsafeLazy
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.getValue

/**
 * Базовая реализация компонента с набором полезных расширений.
 */
abstract class Component(context: ComponentContext) : ComposeComponent, ComponentContext by context {
    private val scope by unsafeLazy { lifecycle.createCoroutineScope() }

    /**
     * Укороченная версия [CoroutineScope.launch] использующая в качестве скоупа [scope].
     *
     * Так же отличается тем что возвращает [Unit] вместо [Job] для возможности использования короткого синтаксиса:
     * ```
     * fun sampleFunction() = launch { ... }
     * ```
     */
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        scope.launch(context, start, block)
    }

    /**
     * Подписка на [StateFlow] через [asValue] с использованием локального scope компонента.
     */
    protected fun <T : Any> StateFlow<T>.asValue(): Value<T> = asValue(scope)

    /**
     * Создает или возвращает созданную ранее [ViewModel] используя для этого [instanceKeeper].
     */
    protected open fun <T : ViewModel> viewModel(factory: () -> T): T {
        // Мы не можем использовать inline тут, так как хотим предоставить возможность переопределения
        // этой функции в наследниках, в таких условиях использования класса лямбды фабрики кажется адекватным решением.
        val viewModelHolder = instanceKeeper.getOrCreate(key = factory::class) { ViewModelHolder(factory()) }
        return viewModelHolder.viewModel
    }
}
