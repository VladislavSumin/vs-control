package ru.vs.core.decompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Компонент с поддержкой рендеринга в compose.
 *
 * Библиотека Decompose спроектирована так что бы не зависеть на прямую от ui слоя, но мы используем только compose и
 * потому можем завязаться на него напрямую.
 * Этот интерфейс позволяет нам держать имплементацию ui приватной внутри модуля фичи, а также позволяет обобщенно
 * отрисовывать любые компоненты.
 */
interface ComposeComponent {
    // Мы не можем использовать default value тут, потому что compose в данный момент не поддерживает параметры по
    // умолчанию для абстрактных compose функций.
    @Composable
    fun Render(modifier: Modifier)
}
