package ru.vs.core.decompose.context

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.StateKeeper

interface VsComponentContext : GenericComponentContext<VsComponentContext> {
    /**
     * Пример расширения контекста
     */
    val testData: String
}

class DefaultVsComponentContext private constructor(
    override val lifecycle: Lifecycle,
    override val stateKeeper: StateKeeper,
    override val instanceKeeper: InstanceKeeper,
    override val backHandler: BackHandler,

    override val testData: String,
) : VsComponentContext {

    override val componentContextFactory =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            DefaultVsComponentContext(lifecycle, stateKeeper, instanceKeeper, backHandler, testData)
        }

    constructor(context: ComponentContext, testData: String) : this(
        context.lifecycle,
        context.stateKeeper,
        context.instanceKeeper,
        context.backHandler,
        testData,
    )
}
