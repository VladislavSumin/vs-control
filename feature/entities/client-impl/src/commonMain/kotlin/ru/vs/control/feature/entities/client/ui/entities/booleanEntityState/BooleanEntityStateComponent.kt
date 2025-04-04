package ru.vs.control.feature.entities.client.ui.entities.booleanEntityState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.client.ui.entities.entityState.BaseEntityStateComponent
import ru.vs.control.feature.entities.domain.baseEntityStates.BooleanEntityState

// TODO
// @GenerateEntityStateComponentFactory
internal class BooleanEntityStateComponent(
    state: StateFlow<Entity<BooleanEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<BooleanEntityState>(state, context) {
    @Composable
    override fun Render(modifier: Modifier) = BooleanEntityStateContent(this)
}
