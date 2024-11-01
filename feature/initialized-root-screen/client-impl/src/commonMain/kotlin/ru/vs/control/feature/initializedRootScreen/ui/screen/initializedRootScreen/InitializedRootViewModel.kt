package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.Navigation

internal class InitializedRootViewModelFactory(private val navigation: Navigation) {
    fun create(): InitializedRootViewModel {
        return InitializedRootViewModel(navigation)
    }
}

internal class InitializedRootViewModel(
    val navigation: Navigation,
) : ViewModel() {
    fun onDeeplink(deeplink: String) {
        // TODO временная реализация диплинков для дебага что бы быстро открывать нужный экран при разработке.
        if (deeplink.startsWith("vs-control://")) {
            val path = deeplink.removePrefix("vs-control://")
            val screenParams = navigation.findDefaultScreenParamsByDebugName(path)
            if (screenParams != null) {
                navigation.open(screenParams)
            }
        }
    }
}
