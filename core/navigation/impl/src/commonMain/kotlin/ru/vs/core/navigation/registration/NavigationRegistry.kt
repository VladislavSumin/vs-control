package ru.vs.core.navigation.registration

import kotlinx.serialization.KSerializer
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

/**
 * Позволяет регистрировать компоненты навигации.
 * Использовать напрямую этот интерфейс нельзя так как его состояние финализируется в процессе инициализации приложения.
 * Для доступа к [NavigationRegistry] воспользуйтесь [NavigationRegistrar].
 */
interface NavigationRegistry {

    /**
     * Регистрирует экран.
     *
     * @param P тип параметров экрана.
     * @param S тип экрана.
     * @param key ключ экрана.
     * @param factory фабрика компонента экрана.
     * @param paramsSerializer сериализатор для [P].
     * @param nameForLogs название экрана для логирования. Нужно так как мы не можем использовать class.qualifiedName
     * в js.
     * @param defaultParams параметры экрана по умолчанию.
     * @param opensIn в каких [NavigationHost] может быть открыт этот экран.
     * @param navigationHosts хосты навигации расположенные на этом экране
     * @param description опциональное описание экрана, используется только для дебага, при отображении графа навигации
     */
    fun <P : ScreenParams, S : Screen> registerScreen(
        key: ScreenKey<P>,
        factory: ScreenFactory<P, S>,
        paramsSerializer: KSerializer<P>,
        nameForLogs: String,
        defaultParams: P? = null,
        opensIn: Set<NavigationHost> = emptySet(),
        navigationHosts: Set<NavigationHost> = emptySet(),
        description: String? = null,
    )
}
