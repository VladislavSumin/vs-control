package ru.vs.core.navigation.screen

import ru.vladislavsumin.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenPath.PathElement
import ru.vs.core.navigation.screen.ScreenPath.PathElement.Params

/**
 * Путь до экрана.
 * Относительные пути не используются, любой путь должен начинаться от корня графа.
 */
internal data class ScreenPath(val path: List<PathElement>) : List<PathElement> by path {

    constructor(screenParams: Iterable<ScreenParams>) : this(screenParams.map { Params(it) })

    constructor(screenParams: ScreenParams) : this(listOf(screenParams))

    operator fun plus(screenParams: ScreenParams): ScreenPath {
        return ScreenPath(path + Params(screenParams))
    }

    fun parent() = ScreenPath(dropLast(1))

    /**
     * Элемент пути, может быть ключом экрана, а может быть параметрами конкретного инстанса экрана.
     */
    sealed interface PathElement {
        fun asErasedKey(): ScreenKey<*>

        data class Key(val screenKey: ScreenKey<*>) : PathElement {
            override fun asErasedKey(): ScreenKey<*> = screenKey
        }

        data class Params(val screenParams: ScreenParams) : PathElement {
            override fun asErasedKey(): ScreenKey<*> = screenParams.asErasedKey()
        }
    }
}
