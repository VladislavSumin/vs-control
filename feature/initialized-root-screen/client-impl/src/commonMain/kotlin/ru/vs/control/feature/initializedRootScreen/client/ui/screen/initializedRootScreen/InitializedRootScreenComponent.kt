package ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.host.childNavigationRoot
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext

/**
 * @param onContentReady необходимо вызвать после готовности к отображению контента. Таким образом можно придержать
 * splash экран на время загрузки контента.
 */
@GenerateFactory(InitializedRootScreenFactory::class)
internal class InitializedRootScreenComponent(
    viewModelFactory: InitializedRootViewModelFactory,
    onContentReady: () -> Unit,
    deeplink: ReceiveChannel<String>,
    context: VsComponentContext,
) : VsComponent(context), ComposeComponent {

    private val viewModel = viewModel { viewModelFactory.create() }

    init {
        // Пробуем обработать первый диплинк синхронно, до инициализации навигации, если таковой существует
        // то мы должны сразу его обработать и при необходимости провести навигацию, так как иначе у нас откроется
        // цепочка экранов по умолчанию и только потом произойдет переход по диплинку.
        deeplink.tryReceive().getOrNull()?.also(viewModel::onDeeplink)
        // Последующие диплинки уже можем обрабатывать не синхронно.
        // Обратите внимание, из-за особенности работы корутин мы не можем быть уверены, что код внутри launch будет
        // запущен синхронно, поэтому нам необходима явная проверка выше.
        // Ответ на вопрос почему так - https://github.com/Kotlin/kotlinx.coroutines/issues/4276
        launch { deeplink.consumeEach(viewModel::onDeeplink) }
    }

    // Вызывается после блока init выше так как мы хотим сначала обработать стартовый диплинк и только потом
    // инициализировать навигацию.
    private val rootNavigation = context.childNavigationRoot(
        navigation = viewModel.navigation,
        onContentReady = onContentReady,
    )

    @Composable
    override fun Render(modifier: Modifier) {
        rootNavigation.Render(modifier)
    }
}
