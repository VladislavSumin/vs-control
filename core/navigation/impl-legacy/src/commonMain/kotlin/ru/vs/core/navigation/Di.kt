package ru.vs.core.navigation

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.navigation.Navigation
import ru.vladislavsumin.core.navigation.registration.NavigationRegistrar
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramViewModelFactory

fun Modules.coreNavigation() = DI.Module("core-navigation") {
    // Декларируем множество, в которое будут собраны все регистраторы навигации в приложении.
    bindSet<NavigationRegistrar>()

    // Я не нашел, как нормально разорвать цикл зависимостей в kodein, поэтому пришлось добавить такой костыль.
    var navigation: Navigation? = null

    bindSingleton { Navigation(registrars = i()).also { navigation = it } }

    bindSingleton {
        val viewModelFactory = NavigationGraphUmlDiagramViewModelFactory { navigation!! }
        NavigationGraphUmlDiagramComponentFactory(viewModelFactory)
    }
}
