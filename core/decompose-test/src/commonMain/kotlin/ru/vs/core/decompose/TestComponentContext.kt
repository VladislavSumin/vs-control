package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume

class TestComponentContext internal constructor(
    lifecycleRegistry: LifecycleRegistry,
    componentContext: ComponentContext,
) : ComponentContext by componentContext, LifecycleRegistry by lifecycleRegistry

/**
 * Создает удобный тестовый [ComponentContext], где все функции управления вынесены наружу.
 */
fun TestComponentContext(): TestComponentContext {
    val lifecycleRegistry = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycleRegistry)
    return TestComponentContext(
        lifecycleRegistry,
        context,
    )
}

fun ResumedTestComponentContext(): TestComponentContext {
    val context = TestComponentContext()
    context.resume()
    return context
}
