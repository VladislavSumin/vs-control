package ru.vs.control.feature.entities.client.ui.entities.entityState

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.domain.EntityState
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
