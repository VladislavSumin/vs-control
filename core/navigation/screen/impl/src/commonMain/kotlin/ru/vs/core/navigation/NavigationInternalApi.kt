package ru.vs.core.navigation

/**
 * Некоторое навигационное api не предназначено для внешнего использования.
 * Подробности описаны в каждом аннотированном поле.
 */
@RequiresOptIn(
    message = "Это внутреннее api навигации, смотрите документацию.",
    level = RequiresOptIn.Level.ERROR
)
@Retention(AnnotationRetention.BINARY)
annotation class NavigationInternalApi
