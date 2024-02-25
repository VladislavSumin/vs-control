package ru.vs.core.navigation.screen

import ru.vs.core.navigation.ScreenParams

/**
 * Путь до экрана.
 * Относительные пути не используются, любой путь должен начинаться от корня графа.
 */
data class ScreenPath(val path: List<ScreenParams>) {
    operator fun plus(screenParams: ScreenParams): ScreenPath {
        return ScreenPath(path + screenParams)
    }
}
