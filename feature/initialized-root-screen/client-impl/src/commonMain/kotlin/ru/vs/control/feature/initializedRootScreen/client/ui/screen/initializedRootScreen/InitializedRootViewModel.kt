package ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen

import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.logger.api.logger
import ru.vladislavsumin.core.navigation.GenericNavigation
import ru.vs.core.decompose.context.VsComponentContext

internal class InitializedRootViewModelFactory(private val navigation: GenericNavigation<VsComponentContext>) {
    fun create(): InitializedRootViewModel {
        return InitializedRootViewModel(navigation)
    }
}

internal class InitializedRootViewModel(
    val navigation: GenericNavigation<VsComponentContext>,
) : ViewModel() {
    fun onDeeplink(deeplink: String) {
        deeplinkLogger.i { "Handle deeplink $deeplink" }
        // TODO временная реализация диплинков для дебага что бы быстро открывать нужный экран при разработке.
        if (deeplink.startsWith("vs-control://")) {
            val path = deeplink.removePrefix("vs-control://")
            val screenParams = navigation.findDefaultScreenParamsByName(path)
            if (screenParams != null) {
                navigation.open(screenParams)
            }
        }
    }

    companion object {
        private val deeplinkLogger = logger("deeplink")
    }
}
