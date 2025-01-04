package ru.vs.control.entities.ui.entities.entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState

abstract class BaseEntityStateComponent<T : EntityState>(
    override val entityState: StateFlow<Entity<out T>>,
    context: ComponentContext,
) : EntityStateComponent<T>, ComponentContext by context
