package ru.vs.core.navigation.screen

import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.ViewModel
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.utils.unsafeLazy
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.getValue

/**
 * Базовая реализация экрана с набором полезных расширений.
 */
abstract class Screen(context: ScreenContext) : ComposeComponent, ScreenContext by context {
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
     * Создает или возвращает созданную ранее [ViewModel] используя для этого [instanceKeeper].
     */
    protected inline fun <reified T : ViewModel> viewModel(factory: () -> T): T {
        return instanceKeeper.getOrCreate { factory() }
    }
}
