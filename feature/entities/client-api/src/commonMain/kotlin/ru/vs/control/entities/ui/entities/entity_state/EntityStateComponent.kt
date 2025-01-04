package ru.vs.control.entities.ui.entities.entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState
import ru.vs.core.decompose.ComposeComponent

/**
 * Base component for render [Entity] with some [EntityState]
 * To known how to register our own [EntityStateComponent] see [EntityStateComponentFactory] documentation
 *
 * When implement this interface recommended to use [BaseEntityStateComponent] as parent class
 */
interface EntityStateComponent<T : EntityState> : ComposeComponent, ComponentContext {
    val entityState: StateFlow<Entity<out T>>
}
