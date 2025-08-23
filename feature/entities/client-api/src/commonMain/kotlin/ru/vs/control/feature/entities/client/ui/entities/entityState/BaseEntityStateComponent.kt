package ru.vs.control.feature.entities.client.ui.entities.entityState

import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.domain.EntityState
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext

abstract class BaseEntityStateComponent<T : EntityState>(
    override val entityState: StateFlow<Entity<out T>>,
    context: VsComponentContext,
) : VsComponent(context), EntityStateComponent<T>
