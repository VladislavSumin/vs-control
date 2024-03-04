package ru.vs.core.navigation.graph

import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.repository.NavigationRepositoryImpl

/**
 * Предоставляет доступ к основному графу навигации.
 * Прямое использование графа не предусмотрено фреймворком, необходимо только передать его в корневой хост навигации.
 */
// TODO удалить, хватит дерева.
class NavigationGraph internal constructor(
    registrars: Set<NavigationRegistrar>,
) {
    internal val repository: NavigationRepository = NavigationRepositoryImpl(registrars)
    internal val tree = NavigationTree(repository)
}
