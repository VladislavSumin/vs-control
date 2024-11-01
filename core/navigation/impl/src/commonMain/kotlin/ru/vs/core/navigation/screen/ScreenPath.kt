package ru.vs.core.navigation.screen

import ru.vs.core.navigation.ScreenParams

/**
 * Путь до экрана.
 * Относительные пути не используются, любой путь должен начинаться от корня графа.
 */
internal data class ScreenPath(val path: List<ScreenParams>) :
    List<ScreenParams> by path {

    constructor(screenParams: ScreenParams) : this(listOf(screenParams))

    operator fun plus(screenParams: ScreenParams): ScreenPath {
        return ScreenPath(path + screenParams)
    }

    fun parent(): ScreenPath {
        return ScreenPath(dropLast(1))
    }
}
