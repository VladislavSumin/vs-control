package ru.vs.control.feature.servers.ui.screen.addServerScreen.items

internal sealed interface AddServerItem {
    sealed interface Simple : AddServerItem

    data object AddServerByUrl : Simple
    data object AddServerByQrCode : Simple
    data class AddEmbeddedServer(val isSupported: Boolean) : Simple

    /**
     * Сервер добавленный в сборку на этапе компиляции.
     */
    data class AddPrebuildServer(val name: String, val url: String) : Simple

    /**
     * Карточка поиска серверов в локальной сети.
     */
    data object SearchServersInLocalNetwork : AddServerItem
}
