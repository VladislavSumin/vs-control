package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.collections.tree.TreeNodeImpl
import ru.vladislavsumin.core.collections.tree.map
import ru.vladislavsumin.core.collections.tree.nodeOf
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.ui.debug.uml.ExternalNavigationGraphUmlNode
import ru.vladislavsumin.core.navigation.ui.debug.uml.InternalNavigationGraphUmlNode
import ru.vladislavsumin.core.navigation.ui.debug.uml.NavigationGraphUmlNode
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class DebugViewModel : NavigationViewModel() {

    fun onClickBack() = close()

    /**
     * Не все пути навигации содержаться в графе навигации. Несколько первых экранов туда не попадают, поэтому
     * добавляем их вручную.
     */
    fun generateFakeNavigationNodesFist(
        originalNode: TreeNodeImpl<InternalNavigationGraphUmlNode>,
    ): TreeNodeImpl<NavigationGraphUmlNode> {
        return nodeOf(
            value = ExternalNavigationGraphUmlNode(
                name = "RootScreenComponent",
                description = "Корневой экран, отвечает за запуск инициализации приложения",
            ),
            nodeOf(
                ExternalNavigationGraphUmlNode(
                    name = "SplashScreenComponent",
                    description = "Отображает splash заглушку",
                ),
            ),
            nodeOf(
                ExternalNavigationGraphUmlNode(
                    name = "InitializedRootScreenComponent",
                    description = """
                    Отображается после инициализации приложения. 
                    Является точкой входа в полноценную навигацию.
                    """.trimIndent(),
                ),
                originalNode.map {
                    it.externalCopy(name = it.name.substringBeforeLast("ScreenParams"))
                },
            ),
        )
    }
}
