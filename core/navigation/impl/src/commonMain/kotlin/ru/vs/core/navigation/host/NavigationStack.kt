package ru.vs.core.navigation.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.navigator.HostNavigator
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.asErasedKey
import ru.vs.core.navigation.screen.wrapWithScreenContext

/**
 * Навигация типа "стек", означает что в ней одновременно может быть несколько экранов, но только последний из них
 * виден пользователю.
 *
 * @param navigationHost навигационный хост для возможности понять какие экраны будут открываться в этой навигации.
 * @param initialStack начальный стек который будет открыт в данной навигации. Обратите внимание стек должен содержать
 * как минимум один элемент.
 * @param key уникальный в пределах экрана ключ для навигации.
 * @param handleBackButton будет ли эта навигация перехватывать нажатия назад.
 */
fun ScreenContext.childNavigationStack(
    navigationHost: NavigationHost,
    initialStack: () -> List<ScreenParams>,
    key: String = "stack_navigation",
    handleBackButton: Boolean = false,
): Value<ChildStack<ScreenParams, Screen>> {
    val source = StackNavigation<ScreenParams>()
    val stack = childStack(
        source = source,
        serializer = navigator.globalNavigator.serializer,
        key = key,
        initialStack = initialStack,
        handleBackButton = handleBackButton,
        childFactory = { screenParams: ScreenParams, context: ComponentContext ->
            val screenContext = context.wrapWithScreenContext(navigator, screenParams)
            val screenFactory = navigator.getChildScreenFactory(screenParams.asErasedKey())
            screenFactory.create(screenContext, screenParams)
        },
    )

    val hostNavigator = StackHostNavigator(source)
    navigator.registerHostNavigator(navigationHost, hostNavigator)
    return stack
}

private class StackHostNavigator(
    private val stackNavigation: StackNavigation<ScreenParams>,
) : HostNavigator {
    override fun open(params: ScreenParams) {
        // Если такого экрана еще нет в стеке, то открываем его.
        // Если же экран уже есть в стеке, то закрываем все экраны после него.
        stackNavigation.navigate(
            transformer = { stack ->
                val indexOfScreen = stack.indexOf(params)
                if (indexOfScreen >= 0) {
                    stack.subList(0, indexOfScreen + 1)
                } else {
                    stack + params
                }
            },
            onComplete = { _, _ -> },
        )
    }

    override fun close(params: ScreenParams): Boolean {
        // Если закрываемый экран расположен первым, то закрываем все экраны КРОМЕ этого, так как в стеке должен быть
        // хотя бы один экран.
        // Если закрываемый экран расположен вторым или далее, то закрываем этот экран и все после него.
        // Если закрываемого экрана нет в стеке, то ничего не делаем.
        var isSuccess: Boolean? = null
        stackNavigation.navigate(
            transformer = { stack ->
                val indexOfScreen = stack.indexOf(params)
                if (indexOfScreen >= 0) {
                    isSuccess = false
                    stack.subList(0, indexOfScreen)
                } else {
                    if (indexOfScreen == 0) {
                        isSuccess = false
                        listOf(params)
                    } else {
                        isSuccess = true
                        stack.subList(0, indexOfScreen)
                    }
                }
            },
            onComplete = { _, _ -> },
        )
        return isSuccess ?: error("unreachable")
    }

    override fun close(screenKey: ScreenKey<ScreenParams>): Boolean {
        // То же самое как при закрытии по инстансу экрана, но ищем по ключу с конца стека до первого найденного экрана.
        var isSuccess: Boolean? = null
        stackNavigation.navigate(
            transformer = { stack ->
                val keysStack = stack.map { it.asErasedKey() }
                val indexOfScreen = keysStack.indexOfLast { it == screenKey }
                if (indexOfScreen >= 0) {
                    isSuccess = false
                    stack.subList(0, indexOfScreen)
                } else {
                    if (indexOfScreen == 0) {
                        isSuccess = false
                        stack.subList(0, 1)
                    } else {
                        isSuccess = true
                        stack.subList(0, indexOfScreen)
                    }
                }
            },
            onComplete = { _, _ -> },
        )
        return isSuccess ?: error("unreachable")
    }
}
