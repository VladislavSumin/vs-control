package ru.vs.control.feature.servers.ui.screen.addServerScreen.items

internal sealed interface AddServerItem {
    sealed interface Simple : AddServerItem
    data object AddServerByUrl : Simple
}
