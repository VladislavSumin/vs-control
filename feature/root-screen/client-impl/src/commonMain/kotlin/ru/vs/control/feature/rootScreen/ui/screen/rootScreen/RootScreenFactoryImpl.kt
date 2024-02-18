package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import com.arkivanov.decompose.ComponentContext

internal class RootScreenFactoryImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
) : RootScreenFactory {
    override fun create(context: ComponentContext): RootScreenComponent {
        return RootScreenComponentImpl(rootScreenViewModelFactory, context)
    }
}
