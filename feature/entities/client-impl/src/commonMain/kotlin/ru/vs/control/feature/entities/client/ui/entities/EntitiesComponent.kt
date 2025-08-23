package ru.vs.control.feature.entities.client.ui.entities

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.value.Value
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponent
import ru.vs.control.feature.entities.client.ui.entities.entityState.EntityStateComponentFactoryRegistry
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.router.list.childListWithState

internal class EntitiesComponent(
    private val entityStateComponentFactoryRegistry: EntityStateComponentFactoryRegistry,
    entitiesViewModelFactory: EntitiesViewModelFactory,
    context: VsComponentContext,
) : VsComponent(context), ComposeComponent {
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

internal class EntitiesComponentFactoryImpl(
    private val entityStateComponentFactoryRegistry: EntityStateComponentFactoryRegistry,
    private val entitiesViewModelFactory: EntitiesViewModelFactory,
) : EntitiesComponentFactory {
    override fun create(componentContext: VsComponentContext): ComposeComponent {
        return EntitiesComponent(
            entityStateComponentFactoryRegistry,
            entitiesViewModelFactory,
            componentContext,
        )
    }
}
