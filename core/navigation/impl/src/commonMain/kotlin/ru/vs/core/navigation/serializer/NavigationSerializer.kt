package ru.vs.core.navigation.serializer

import com.arkivanov.essenty.statekeeper.ExperimentalStateKeeperApi
import com.arkivanov.essenty.statekeeper.polymorphicSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.repository.NavigationRepository
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.reflect.KClass

internal class NavigationSerializer(
    repository: NavigationRepository,
) {
    /**
     * Сериализатор для всех зарегистрированных [ScreenParams], используется внутри decompose для сохранения и
     * восстановления состояния приложения.
     */
    @OptIn(ExperimentalSerializationApi::class, ExperimentalStateKeeperApi::class)
    val serializer = polymorphicSerializer(
        ScreenParams::class,
        SerializersModule {
            polymorphic(ScreenParams::class) {
                repository.serializers.forEach { (clazz, serializer) ->
                    subclass(clazz.key as KClass<ScreenParams>, serializer as KSerializer<ScreenParams>)
                }
            }
        },
    )
}
