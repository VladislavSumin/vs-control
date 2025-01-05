package ru.vs.control.feature.entities.client.ui.entities.entityState

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.domain.EntityState

abstract class BaseEntityStateComponent<T : EntityState>(
    override val entityState: StateFlow<Entity<out T>>,
    context: ComponentContext,
) : EntityStateComponent<T>, ComponentContext by context
