package ru.vs.core.navigation.screen

import ru.vladislavsumin.core.navigation.ScreenParams

/**
 * Фабрика для создания компонента экрана.
 * @param P тип параметров экрана.
 * @param S тип экрана.
 */
interface ScreenFactory<P : ScreenParams, S : Screen> {
    /**
     * Создает компонент экрана.
     * @param context контекст экрана.
     * @param params параметры экрана.
     */
    fun create(context: ScreenContext, params: P): S
}
