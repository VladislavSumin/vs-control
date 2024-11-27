package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.Component
import ru.vs.core.decompose.LazyListComponent

internal class EmbeddedServersListComponent(
    viewModelFactory: EmbeddedServersListViewModelFactory,
    context: ComponentContext,
) : Component(context),
    LazyListComponent {

    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun rememberRenderer(): LazyListComponent.Renderer {
        val state = viewModel.state.collectAsState().value
        return LazyListComponent.Renderer { list ->
            list.items(
                items = state,
                key = { it.id.raw },
                contentType = { EmbeddedServersListContentType },
            ) { EmbeddedServerContent(viewModel, it) }
        }
    }
}

private object EmbeddedServersListContentType
