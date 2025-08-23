package ru.vs.control.feature.entitiesScreen.client.ui.screen

import ru.vladislavsumin.core.navigation.registration.NavigationRegistry
import ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen.EntitiesScreenFactory
import ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen.EntitiesScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsNavigationRegistrar

internal class NavigationRegistrarImpl(
    private val entitiesScreenFactory: EntitiesScreenFactory,
) : VsNavigationRegistrar {
    override fun NavigationRegistry<VsComponentContext>.register() {
        registerScreen(
            factory = entitiesScreenFactory,
            defaultParams = EntitiesScreenParams,
            description = "Экран со списком всех Entities",
        )
    }
}
