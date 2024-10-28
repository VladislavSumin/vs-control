package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Stable

@Stable
data class NavigationGraphUmlNode(
    val info: Info,
    val children: List<NavigationGraphUmlNode>,
) {
    /**
     * @param name название параметров экрана.
     * @param hasDefaultParams есть ли у экрана параметры по умолчанию.
     * @param isPartOfMainGraph является ли эта нода частью навигационного графа.
     */
    data class Info(
        val name: String,
        val hasDefaultParams: Boolean,
        val isPartOfMainGraph: Boolean,
    )
}
