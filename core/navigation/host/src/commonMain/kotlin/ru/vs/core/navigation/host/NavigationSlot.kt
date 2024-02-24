package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import ru.vs.navigation.NavigationGraph
import ru.vs.navigation.NavigationHost
import ru.vs.navigation.Screen
import ru.vs.navigation.ScreenContext
import ru.vs.navigation.ScreenKey
import ru.vs.navigation.ScreenParams

/**
 * TODO
 */
fun ScreenContext.childNavigationSlot(
    navigationHost: NavigationHost,
    initialConfiguration: () -> ScreenParams? = { null },
    handleBackButton: Boolean = false,
): Value<ChildSlot<ScreenParams, Screen>> {
    val source = SlotNavigation<ScreenParams>()
    return childSlot(
        source = source,
        saveConfiguration = { null }, // TODO
        restoreConfiguration = { null }, // TODO
        key = navigationHost::class.qualifiedName!!,
        initialConfiguration = initialConfiguration,
        handleBackButton = handleBackButton,
        childFactory = { screenParams: ScreenParams, context: ComponentContext ->
            val screenContext = SlotScreenContext(navigationGraph, context)
            // TODO описать сейвовость, и подумать над другим строение generics
            val screenKey = ScreenKey(screenParams::class) as ScreenKey<ScreenParams>
            val screenFactory = navigationGraph.findFactory(screenKey) ?: error("TODO")
            screenFactory.create(screenContext, screenParams)
        }
    )
}

// TODO это конечно временное решение, наследование контекстов будет работать не так.
private class SlotScreenContext(
    override val navigationGraph: NavigationGraph,
    context: ComponentContext,
) : ScreenContext, ComponentContext by context
