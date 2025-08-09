package ru.vs.control.feature.entities.client.ui.entities

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import ru.vladislavsumin.core.decompose.components.Component
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponent
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistry
import ru.vs.core.decompose.router.list.childListWithState

// TODO
// @GenerateFactory(EntitiesComponentFactory::class)
internal class EntitiesComponent(
    private val entityStateComponentFactoryRegistry: EntityStateComponentFactoryRegistry,
    entitiesViewModelFactory: EntitiesViewModelFactory,
    context: ComponentContext,
) : Component(context), ComposeComponent {
    private val viewModel: EntitiesViewModel = viewModel { entitiesViewModelFactory.create() }

    val entitiesList: Value<List<EntityStateComponent<*>>> = context.childListWithState(
        state = viewModel.state.asValue(),
        idSelector = { it.id },
        childFactory = { entityState, context ->
            entityStateComponentFactoryRegistry.create(entityState, context)
        },
    )

    @Composable
    override fun Render(modifier: Modifier) = EntitiesContent(this)
}
