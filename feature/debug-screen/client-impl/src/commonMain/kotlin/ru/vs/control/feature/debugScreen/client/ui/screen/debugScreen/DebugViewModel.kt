package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.collections.tree.map
import ru.vladislavsumin.core.collections.tree.nodeOf
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.ui.debug.uml.NavigationGraphUmlNode
import ru.vladislavsumin.core.navigation.ui.debug.uml.NavigationGraphUmlNodeInfo
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class DebugViewModel : NavigationViewModel() {

    fun onClickBack() = close()

    /**
     * Не все пути навигации содержаться в графе навигации. Несколько первых экранов туда не попадают, поэтому
     * добавляем их вручную.
     */
    fun generateFakeNavigationNodesFist(originalNode: NavigationGraphUmlNode): NavigationGraphUmlNode {
        return nodeOf(
            value = NavigationGraphUmlNodeInfo(
                name = "RootScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
                description = "Корневой экран, отвечает за запуск инициализации приложения",
            ),
            nodeOf(
                NavigationGraphUmlNodeInfo(
                    name = "SplashScreenComponent",
                    hasDefaultParams = false,
                    isPartOfMainGraph = false,
                    description = "Отображает splash заглушку",
                ),
            ),
            nodeOf(
                NavigationGraphUmlNodeInfo(
                    name = "InitializedRootScreenComponent",
                    hasDefaultParams = false,
                    isPartOfMainGraph = false,
                    description = """
                    Отображается после инициализации приложения. 
                    Является точкой входа в полноценную навигацию.
                    """.trimIndent(),
                ),
                originalNode.map {
                    it.copy(name = it.name.substringBeforeLast("ScreenParams"))
                },
            ),
        )
    }
}
