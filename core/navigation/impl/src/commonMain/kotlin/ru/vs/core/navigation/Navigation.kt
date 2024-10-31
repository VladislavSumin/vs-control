package ru.vs.core.navigation

import ru.vs.core.navigation.serializer.NavigationSerializer
import ru.vs.core.navigation.tree.NavigationTree

/**
 * TODO доку
 */
class Navigation internal constructor(
    internal val navigationTree: NavigationTree,
    internal val navigationSerializer: NavigationSerializer,
)
