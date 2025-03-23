package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.collections.tree.TreeNode

@Stable
data class NavigationGraphUmlNode(
    override val value: Info,
    override val children: Collection<NavigationGraphUmlNode>,
) : TreeNode<NavigationGraphUmlNode.Info, NavigationGraphUmlNode> {

    /**
     * @param name название параметров экрана.
     * @param hasDefaultParams есть ли у экрана параметры по умолчанию.
     * @param isPartOfMainGraph является ли эта нода частью навигационного графа.
     * @param description любое дополнительное описание на ваше усмотрение.
     */
    data class Info(
        val name: String,
        val hasDefaultParams: Boolean,
        val isPartOfMainGraph: Boolean,
        val description: String? = null,
    )
}
