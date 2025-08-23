package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

@Composable
internal fun MainContent(
    viewModel: MainViewModel,
    tabNavigation: Value<ChildPages<*, ComposeComponent>>,
    modifier: Modifier,
) {
    val navigationState = tabNavigation.subscribeAsState()
    Scaffold(
        modifier,
        bottomBar = { BottomBar(viewModel, navigationState.value.selectedIndex) },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddings ->
        com.arkivanov.decompose.extensions.compose.pages.ChildPages(
            pages = tabNavigation,
            onPageSelected = {},
            Modifier.padding(paddings),
            scrollAnimation = PagesScrollAnimation.Default,
        ) { _, page ->
            page.Render(Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBar(viewModel: MainViewModel, selectedIndex: Int) {
    NavigationBar {
        // TODO перенести во вью модель логику формирования
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = viewModel::onClickEntities,
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Entities") },
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = viewModel::onClicksServers,
            icon = { Icon(Icons.Default.Dns, null) },
            label = { Text("Servers") },
        )
        NavigationBarItem(
            selected = false,
            onClick = viewModel::onClickDebug,
            icon = { Icon(Icons.Default.BugReport, null) },
            label = { Text("Debug") },
        )
    }
}
