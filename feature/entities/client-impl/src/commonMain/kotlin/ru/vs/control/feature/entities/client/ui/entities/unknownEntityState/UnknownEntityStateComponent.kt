package ru.vs.control.feature.entities.client.ui.entities.unknownEntityState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.client.ui.entities.entityState.BaseEntityStateComponent
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.core.decompose.context.VsComponentContext

internal class UnknownEntityStateComponent(
    state: StateFlow<Entity<out EntityState>>,
    context: VsComponentContext,
) : BaseEntityStateComponent<EntityState>(state, context) {
    @Composable
    override fun Render(modifier: Modifier) = UnknownEntityStateContent(this)
}
