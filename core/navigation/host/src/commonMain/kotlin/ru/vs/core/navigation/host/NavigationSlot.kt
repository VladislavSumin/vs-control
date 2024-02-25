package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.vs.core.navigation.GlobalNavigator
import ru.vs.core.navigation.HostNavigator
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.Screen
import ru.vs.core.navigation.ScreenContext
import ru.vs.core.navigation.ScreenKey
import ru.vs.core.navigation.ScreenParams

/**
 * TODO
 */
fun ScreenContext.childNavigationSlot(
    navigationHost: NavigationHost,
    initialConfiguration: () -> ScreenParams? = { null },
    handleBackButton: Boolean = false,
): Value<ChildSlot<ScreenParams, Screen>> {
    val source = SlotNavigation<ScreenParams>()
    val slot = childSlot(
        source = source,
        saveConfiguration = { null }, // TODO
        restoreConfiguration = { null }, // TODO
        key = navigationHost::class.qualifiedName!!,
        initialConfiguration = initialConfiguration,
        handleBackButton = handleBackButton,
        childFactory = { screenParams: ScreenParams, context: ComponentContext ->
            val screenContext = SlotScreenContext(globalNavigator, context)
            // TODO описать сейвовость, и подумать над другим строение generics
            val screenKey = ScreenKey(screenParams::class) as ScreenKey<ScreenParams>
            val screenFactory = globalNavigator.navigationGraph.findFactory(screenKey) ?: error("TODO")
            screenFactory.create(screenContext, screenParams)
        }
    )

    val hostNavigator = SlotHostNavigator(source)
    globalNavigator.registerHostNavigator(hostNavigator)
    lifecycle.doOnDestroy { globalNavigator.unregisterHostNavigator(hostNavigator) }

    return slot
}

private class SlotHostNavigator(
    private val slotNavigation: SlotNavigation<ScreenParams>,
) : HostNavigator {
    override fun open(params: ScreenParams) {
        slotNavigation.navigate { params }
    }

    override fun close(params: ScreenParams): Boolean {
        var isSuccess: Boolean? = null
        slotNavigation.navigate {
            if (params == it) {
                isSuccess = true
                null
            } else {
                isSuccess = false
                it
            }
        }
        return isSuccess ?: error("unreachable")
    }

    override fun close(screenKey: ScreenKey<ScreenParams>): Boolean {
        var isSuccess: Boolean? = null
        slotNavigation.navigate {
            when {
                it == null -> {
                    isSuccess = false
                    it
                }

                screenKey == ScreenKey(it::class) -> {
                    isSuccess = true
                    null
                }

                else -> {
                    isSuccess = false
                    it
                }
            }
        }
        return isSuccess ?: error("unreachable")
    }
}

// TODO это конечно временное решение, наследование контекстов будет работать не так.
private class SlotScreenContext(
    override val globalNavigator: GlobalNavigator,
    context: ComponentContext,
) : ScreenContext, ComponentContext by context
