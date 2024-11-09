package ru.vs.core.navigation.registration

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

/**
 * Позволяет регистрировать компоненты навигации.
 * Использовать напрямую этот интерфейс нельзя так как его состояние финализируется в процессе инициализации приложения.
 * Для доступа к [NavigationRegistry] воспользуйтесь [NavigationRegistrar].
 *
 * Абстрактный класс вместо интерфейса для возможности использовать internal && inline для создания удобного апи.
 */
abstract class NavigationRegistry {
    /**
     * Регистрирует экран.
     *
     * @param P тип параметров экрана.
     * @param S тип экрана.
     * @param factory фабрика компонента экрана.
     * @param defaultParams параметры экрана по умолчанию.
     * @param opensIn в каких [NavigationHost] может быть открыт этот экран.
     * @param navigationHosts хосты навигации расположенные на этом экране
     * @param description опциональное описание экрана, используется только для дебага, при отображении графа навигации
     */
    inline fun <reified P : ScreenParams, S : Screen> registerScreen(
        factory: ScreenFactory<P, S>,
        defaultParams: P? = null,
        opensIn: Set<NavigationHost> = emptySet(),
        navigationHosts: Set<NavigationHost> = emptySet(),
        description: String? = null,
    ) = registerScreen(
        ScreenKey(P::class),
        factory,
        Json.serializersModule.serializer<P>(),
        defaultParams,
        opensIn,
        navigationHosts,
        description,
    )

    @PublishedApi
    internal abstract fun <P : ScreenParams, S : Screen> registerScreen(
        key: ScreenKey<P>,
        factory: ScreenFactory<P, S>,
        paramsSerializer: KSerializer<P>,
        defaultParams: P? = null,
        opensIn: Set<NavigationHost> = emptySet(),
        navigationHosts: Set<NavigationHost> = emptySet(),
        description: String? = null,
    )
}
