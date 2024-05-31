package ru.vs.core.utils

/**
 * Форматирует последовательность в строку добавляя форматирование по умолчанию.
 */
fun <T> Iterable<T>.joinToStingFormatted(transform: ((T) -> CharSequence)? = null): String {
    return joinToString(
        prefix = "[\n\t",
        postfix = "\n]",
        separator = ",\n\t",
        transform = transform,
    )
}
