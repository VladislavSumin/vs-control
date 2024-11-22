package ru.vs.core.navigation.host

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
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.asErasedKey

/**
 * Навигация типа "слот", означает что в ней одновременно может быть только один экран. Пред идущий экран при этом
 * полностью закрывается.
 *
 * @param navigationHost навигационный хост для возможности понять какие экраны будут открываться в этой навигации.
 * @param initialConfiguration начальный экран который будет открыт в данной навигации. Можно использовать null, если
 * в дальнейшем мы будем открывать тут экраны через навигационный граф.
 * @param key уникальный в пределах экрана ключ для навигации.
 * @param handleBackButton будет ли эта навигация перехватывать нажатия назад.
 */
fun ScreenContext.childNavigationSlot(
    navigationHost: NavigationHost,
    initialConfiguration: () -> ScreenParams? = { null },
    key: String = "slot_navigation",
    handleBackButton: Boolean = false,
): Value<ChildSlot<ScreenParams, Screen>> {
    val source = SlotNavigation<ScreenParams>()

    val hostNavigator = SlotHostNavigator(source)
    navigator.registerHostNavigator(navigationHost, hostNavigator)

    val slot = childSlot(
        source = source,
        serializer = navigator.serializer,
        key = key,
        initialConfiguration = { navigator.getInitialParamsFor(navigationHost) ?: initialConfiguration() },
        handleBackButton = handleBackButton,
        childFactory = ::childScreenFactory,
    )

    return slot
}

private class SlotHostNavigator(
    private val slotNavigation: SlotNavigation<ScreenParams>,
) : HostNavigator {
    override fun open(params: ScreenParams) {
        // Просто открываем переданный экран, логика слот навигации закроет предыдущий экран если он другой
        // или не будет делать ничего если уже открыт искомый экран.
        slotNavigation.navigate { params }
    }

    override fun open(
        screenKey: ScreenKey<*>,
        defaultParams: () -> ScreenParams,
    ) {
        // Проверяем, если текущий экран имеет такой же ключ, то оставляем его, иначе заменяем на defaultParams
        slotNavigation.navigate { currentOpenedScreen ->
            if (currentOpenedScreen != null && currentOpenedScreen.asErasedKey() == screenKey) {
                currentOpenedScreen
            } else {
                defaultParams()
            }
        }
    }

    override fun close(params: ScreenParams): Boolean {
        var isSuccess: Boolean? = null
        slotNavigation.navigate { currentOpenedScreen ->
            if (params == currentOpenedScreen) {
                isSuccess = true
                null
            } else {
                isSuccess = false
                currentOpenedScreen
            }
        }
        return isSuccess ?: error("unreachable")
    }

    override fun close(screenKey: ScreenKey<ScreenParams>): Boolean {
        var isSuccess: Boolean? = null
        slotNavigation.navigate {
            when {
                // Все экраны закрыты
                it == null -> {
                    isSuccess = false
                    it
                }

                // Открыт нужный нам экран
                screenKey == it.asErasedKey() -> {
                    isSuccess = true
                    null
                }

                // Открыт другой экран, закрывать нечего.
                else -> {
                    isSuccess = false
                    it
                }
            }
        }
        return isSuccess ?: error("unreachable")
    }
}
