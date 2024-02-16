package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import com.arkivanov.decompose.ComponentContext

interface RootScreenFactory {
    fun create(context: ComponentContext): RootScreenComponent
}
