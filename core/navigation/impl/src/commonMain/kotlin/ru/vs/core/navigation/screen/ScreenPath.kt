package ru.vs.core.navigation.screen

import ru.vs.core.collections.tree.TreePath
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.tree.ScreenInfo

/**
 * Путь до экрана.
 * Относительные пути не используются, любой путь должен начинаться от корня графа.
 */
internal data class ScreenPath(val path: List<ScreenParams>) :
    TreePath<ScreenInfo, ScreenParams>,
    List<ScreenParams> by path {

    constructor(screenParams: ScreenParams) : this(listOf(screenParams))

    operator fun plus(screenParams: ScreenParams): ScreenPath {
        return ScreenPath(path + screenParams)
    }

    override val keyMatcher: (ScreenInfo, ScreenParams) -> Boolean =
        { data, key -> data.screenKey == key.asErasedKey() }

    fun parent(): ScreenPath {
        return ScreenPath(dropLast(1))
    }
}
