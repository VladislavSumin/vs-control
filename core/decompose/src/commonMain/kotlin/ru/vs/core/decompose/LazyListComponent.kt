package ru.vs.core.decompose

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

/**
 * Компонент с поддержкой рендеринга в [androidx.compose.foundation.lazy.LazyColumn] и его аналоги.
 * Реализация немного сложнее, чем с простым [ComposeComponent] из-за особенностей ленивых списков.
 */
interface LazyListComponent {
    /**
     * Сохраняет необходимые состояния в иерархии Compose и возвращает [Renderer] который можно использовать
     * внутри [LazyListScope] для отображения листа.
     */
    @Composable
    fun rememberRenderer(): Renderer

    /**
     * Позволяет отрисовать контент внутри [androidx.compose.foundation.lazy.LazyColumn].
     */
    fun interface Renderer {
        fun renderIn(scope: LazyListScope)
    }
}
