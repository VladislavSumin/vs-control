package ru.vs.core.navigation

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.repository.NavigationRepositoryImpl
import ru.vs.core.navigation.tree.NavigationTree
import ru.vs.core.navigation.ui.debug.NavigationGraphUmlDiagramComponentFactory

fun Modules.coreNavigation() = DI.Module("core-navigation") {
    // Декларируем множество в которое будут собраны все регистраторы навигации в приложении.
    bindSet<NavigationRegistrar>()

    // Репозиторий используется только для построения NavigationTree, и далее не нужен.
    bindProvider<NavigationRepository> { NavigationRepositoryImpl(i()) }
    bindSingleton { NavigationTree(i()) }
    bindSingleton { NavigationGraphUmlDiagramComponentFactory() }
}
