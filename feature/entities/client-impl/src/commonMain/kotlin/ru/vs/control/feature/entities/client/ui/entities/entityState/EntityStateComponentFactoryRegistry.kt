package ru.vs.control.feature.entities.client.ui.entities.entityState

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.feature.entities.client.domain.Entity
import ru.vs.control.feature.entities.client.ui.entities.unknownEntityState.UnknownEntityStateComponent
import kotlin.reflect.KClass

internal interface EntityStateComponentFactoryRegistry {
    /**
     * Creates instance of implementation [EntityStateComponent] for given [Entity.primaryState]
     * If [Entity.primaryState] is unknown create instance of [UnknownEntityStateComponent]
     */
    fun create(
        state: StateFlow<Entity<out EntityState>>,
        context: ComponentContext,
    ): EntityStateComponent<*>
}

internal class EntityStateComponentFactoryRegistryImpl(
    factoriesSet: Set<EntityStateComponentFactory<*>>,
) : EntityStateComponentFactoryRegistry {
    private val factories: Map<KClass<out EntityState>, EntityStateComponentFactory<*>> =
        factoriesSet.associateBy { it.entityStateType }

    override fun create(state: StateFlow<Entity<out EntityState>>, context: ComponentContext): EntityStateComponent<*> {
        val factory = factories[state.value.primaryState::class]
        // We check types manually when registering factory, no additional check needed
        @Suppress("UNCHECKED_CAST")
        return factory?.create(state as StateFlow<Nothing>, context) ?: UnknownEntityStateComponent(state, context)
    }
}
