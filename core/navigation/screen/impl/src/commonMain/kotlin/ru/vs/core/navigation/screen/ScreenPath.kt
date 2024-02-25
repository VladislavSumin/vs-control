package ru.vs.core.navigation.screen

import ru.vs.core.navigation.ScreenParams

/**
 * Путь до экрана.
 */
data class ScreenPath(val path: List<ScreenParams>) {
    operator fun plus(screenParams: ScreenParams): ScreenPath {
        return ScreenPath(path + screenParams)
    }
}
