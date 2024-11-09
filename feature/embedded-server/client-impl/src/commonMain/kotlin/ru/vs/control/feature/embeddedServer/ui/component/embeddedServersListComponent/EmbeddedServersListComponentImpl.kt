package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import androidx.compose.foundation.lazy.LazyListScope
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.Component

internal class EmbeddedServersListComponentImpl(
    viewModelFactory: EmbeddedServersListViewModelFactory,
    context: ComponentContext,
) : Component(context),
    EmbeddedServersListComponent {

    private val viewModel = viewModel { viewModelFactory.create() }

    override fun renderIn(list: LazyListScope) {
        // TODO
    }
}
