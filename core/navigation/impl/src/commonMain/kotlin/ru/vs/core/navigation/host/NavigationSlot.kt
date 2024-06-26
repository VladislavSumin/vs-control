package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.value.Value
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.HostNavigator
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

/**
 * TODO
 * @param key уникальный в пределах экрана ключ для навигации.
 *
 * TODO мы не можем использовать navigationHost::class.qualifiedName!! в качестве имени экрана, так как такой вызов
 * рефлексии пока не поддерживается в котлине (версия 2.0.0)
 *
 */
fun ScreenContext.childNavigationSlot(
    navigationHost: NavigationHost,
    initialConfiguration: () -> ScreenParams? = { null },
    key: String,
    handleBackButton: Boolean = false,
): Value<ChildSlot<ScreenParams, Screen>> {
    val source = SlotNavigation<ScreenParams>()
    val slot = childSlot(
        source = source,
        serializer = navigator.globalNavigator.navigationTree.serializer,
        key = key,
        initialConfiguration = initialConfiguration,
        handleBackButton = handleBackButton,
        childFactory = { screenParams: ScreenParams, context: ComponentContext ->
            val screenContext = context.wrapWithScreenContext(navigator, screenParams)
            // TODO описать сейвовость, и подумать над другим строение generics
            val screenKey = ScreenKey(screenParams::class) as ScreenKey<ScreenParams>
            val screenFactory = navigator.node.children[screenKey]!!.screenRegistration.factory
                as ScreenFactory<ScreenParams, out Screen>
            screenFactory.create(screenContext, screenParams)
        },
    )

    val hostNavigator = SlotHostNavigator(source)
    navigator.registerHostNavigator(navigationHost, hostNavigator)
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
