package ru.vs.control.feature.servers.ui.screen.addServerScreen.items

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

/**
 * Простой компонент добавления сервера (без дополнительной логики), по сути просто кнопка.
 */
internal class SimpleAddServerItemComponent(
    val item: AddServerItem.Simple,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Text("Simple Item component")
    }
}
