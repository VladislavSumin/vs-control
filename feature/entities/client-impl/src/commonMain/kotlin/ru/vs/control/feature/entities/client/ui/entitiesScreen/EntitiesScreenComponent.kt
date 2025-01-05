package ru.vs.control.feature.entities.client.ui.entitiesScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vs.control.feature.entities.client.ui.entities.EntitiesComponentFactory
import ru.vs.core.decompose.ComposeComponent

@Stable
internal interface EntitiesScreenComponent {
    val entitiesComponent: ComposeComponent
}

// TODO
// @GenerateFactory(EntitiesScreenComponentFactory::class)
internal class EntitiesScreenComponentImpl(
    entitiesComponentFactory: EntitiesComponentFactory,
    componentContext: ComponentContext,
) : EntitiesScreenComponent, ComposeComponent, ComponentContext by componentContext {

    override val entitiesComponent = entitiesComponentFactory.create(
        componentContext = childContext("entities-component"),
    )

    @Composable
    override fun Render(modifier: Modifier) = EntitiesScreenContent(this, modifier)
}
