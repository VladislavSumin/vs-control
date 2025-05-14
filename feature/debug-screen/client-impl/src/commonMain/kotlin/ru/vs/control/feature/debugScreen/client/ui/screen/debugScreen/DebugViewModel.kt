package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlNode

@Stable
@GenerateFactory
internal class DebugViewModel : NavigationViewModel() {

    fun onClickBack() = close()

    /**
     * Не все пути навигации содержаться в графе навигации. Несколько первых экранов туда не попадают, поэтому
     * добавляем их вручную.
     */
    fun generateFakeNavigationNodesFist(originalNode: NavigationGraphUmlNode): NavigationGraphUmlNode {
        val initializedRootScreenNode = NavigationGraphUmlNode(
            value = NavigationGraphUmlNode.Info(
                name = "InitializedRootScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
                description = """
                    Отображается после инициализации приложения. 
                    Является точкой входа в полноценную навигацию.
                """.trimIndent(),
            ),
            children = listOf(originalNode),
        )

        val splashScreenNode = NavigationGraphUmlNode(
            value = NavigationGraphUmlNode.Info(
                name = "SplashScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
                description = "Отображает splash заглушку",
            ),
            children = emptyList(),
        )

        val rootScreenNode = NavigationGraphUmlNode(
            value = NavigationGraphUmlNode.Info(
                name = "RootScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
                description = "Корневой экран, отвечает за запуск инициализации приложения",
            ),
            children = listOf(
                splashScreenNode,
                initializedRootScreenNode,
            ),
        )

        return rootScreenNode
    }
}
