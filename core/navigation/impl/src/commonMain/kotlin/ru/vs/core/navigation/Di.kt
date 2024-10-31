package ru.vs.core.navigation

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.repository.NavigationRepositoryImpl
import ru.vs.core.navigation.serializer.NavigationSerializer
import ru.vs.core.navigation.tree.NavigationTreeBuilder
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramViewModelFactory

fun Modules.coreNavigation() = DI.Module("core-navigation") {
    // Декларируем множество в которое будут собраны все регистраторы навигации в приложении.
    bindSet<NavigationRegistrar>()

    // Я не нашел как нормально разорвать цикл зависимостей в kodein поэтому пришлось добавить такой костыль.
    var navigation: Navigation? = null

    bindSingleton {
        val navigationRepository = NavigationRepositoryImpl(i())
        val navigationSerializer = NavigationSerializer(navigationRepository)
        val navigationTreeBuilder = NavigationTreeBuilder(navigationRepository)
        val navigationTree = navigationTreeBuilder.build()
        Navigation(navigationTree, navigationSerializer).also { navigation = it }
    }

    bindSingleton {
        val viewModelFactory = NavigationGraphUmlDiagramViewModelFactory { navigation!! }
        NavigationGraphUmlDiagramComponentFactory(viewModelFactory)
    }
}
