package ru.vs.core.collections.tree

/**
 * Путь в дереве с внешним ключом [K]
 *
 * @param T тип данных в дереве.
 * @param K тип данных ключа.
 */
interface TreePath<T, K> : List<K> {
    /**
     * Проверяет что данные [T] соответствуют ключу [K].
     */
    val keyMatcher: (T, K) -> Boolean
}
