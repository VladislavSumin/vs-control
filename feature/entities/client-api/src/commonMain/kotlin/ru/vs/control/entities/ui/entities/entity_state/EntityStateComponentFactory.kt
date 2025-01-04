package ru.vs.control.entities.ui.entities.entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState
import kotlin.reflect.KClass

/**
 * Factory to create instances of [EntityStateComponent]
 *
 * Bind our factories into root kodein module:
 * ```
 * inBindSet<EntityStateComponentFactory<*>> {
 *     add { singleton { SampleEntityStateComponentFactory() } }
 * }
 * ```
 */
interface EntityStateComponentFactory<T : EntityState> {
    /**
     * Class for type [T], needed to check types before call [create] method
     */
    val entityStateType: KClass<T>

    /**
     * @param state - flow emits actual state for given [Entity]
     * @param context - child context to use as [ComponentContext] delegate when creating [EntityStateComponent]
     */
    fun create(state: StateFlow<Entity<T>>, context: ComponentContext): EntityStateComponent<T>
}
