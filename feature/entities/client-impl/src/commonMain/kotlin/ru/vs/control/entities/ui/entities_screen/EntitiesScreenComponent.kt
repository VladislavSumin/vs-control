package ru.vs.control.entities.ui.entities_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vs.control.entities.ui.entities.EntitiesComponentFactory
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
        componentContext = childContext("entities-component")
    )

    @Composable
    override fun Render(modifier: Modifier) = EntitiesScreenContent(this, modifier)
}
