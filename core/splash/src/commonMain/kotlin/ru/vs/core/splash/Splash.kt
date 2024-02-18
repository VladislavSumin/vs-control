package ru.vs.core.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.statekeeper.SerializableContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.createCoroutineScope

/**
 * TODO компонент пока не готов и находится в процессе разработки.
 *
 * Компонент навигации для реализации splash экрана.
 *
 * @param T тип дочернего компонента.
 * @param key ключ навигации, должен быть уникальным в рамках компонента.
 * @param scope [CoroutineScope] для внутренних нужд, жц должен совпадать с lifecycle компонента.
 * @param awaitInitialization функция для ожидания завершения инициализации приложения.
 * @param splashComponentFactory фабрика для создания компонента splash экрана.
 * @param contentComponentFactory фабрика для создания компонента экрана с контентом. onContentReady необходимо вызвать
 * после того как контентный компонент будет готов к отображению контента, до этого момента будет отображаться splash.
 */
// TODO T не должно расширять ComposeComponent?
fun <T : ComposeComponent> ComponentContext.childSplash(
    key: String = "child-splash",
    scope: CoroutineScope = lifecycle.createCoroutineScope(),
    awaitInitialization: suspend () -> Unit,
    splashComponentFactory: (context: ComponentContext) -> T,
    contentComponentFactory: (onContentReady: () -> Unit, context: ComponentContext) -> T,
): Value<SplashChildState<T>> {
    val navigationSource = SimpleNavigation<SplashNavEvent>()

    scope.launch(Dispatchers.Main.immediate) {
        awaitInitialization()
        navigationSource.navigate(SplashNavEvent.ApplicationInitialized)
    }

    val onContentReady = {
        navigationSource.navigate(SplashNavEvent.ContentReady)
    }

    return children(
        source = navigationSource,
        key = key,
        initialState = { SplashNavState.Splash as SplashNavState },
        saveState = {
            // Мы всегда начинаем с состояния Splash и потому этому виду навигации не нужно сохранять состояние,
            // но если мы вернем null тут, то decompose не сохранит состояние дочерних элементов (контента), поэтому,
            // что бы состояние сохранялось мы возвращаем пустой контейнер.
            SerializableContainer()
        },
        restoreState = {
            // Всегда восстанавливаемся в состояние Splash.
            SplashNavState.Splash
        },
        navTransformer = { _, event ->
            when (event) {
                SplashNavEvent.Splash -> SplashNavState.Splash
                SplashNavEvent.ApplicationInitialized -> SplashNavState.InitializedSplash
                SplashNavEvent.ContentReady -> SplashNavState.Content
            }
        },
        stateMapper = { state, children ->
            // TODO при восстановлении состояния приходит неожиданная конфигурация.
            println("QWQWQW: $state")
            println("QWQWQW: $children")
            SplashChildState(children.last().instance)
        },
        childFactory = { configuration, context ->
            when (configuration) {
                ChildSplashConfiguration.Splash -> splashComponentFactory(context)
                ChildSplashConfiguration.Content -> contentComponentFactory(onContentReady, context)
            }
        }
    )
}

// TODO в процессе разработки, просто накинул тут что бы отрисовать как нибудь.
data class SplashChildState<T>(
    val current: T?
)

private sealed interface SplashNavEvent {
    data object Splash : SplashNavEvent
    data object ApplicationInitialized : SplashNavEvent
    data object ContentReady : SplashNavEvent
}

private sealed interface SplashNavState : NavState<ChildSplashConfiguration> {
    /**
     * Приложение не инициализировано, навигация в состоянии splash экрана.
     */
    data object Splash : SplashNavState {
        override val children: List<ChildNavState<ChildSplashConfiguration>> = listOf(
            SimpleChildNavState(ChildSplashConfiguration.Content, ChildNavState.Status.DESTROYED),
            SimpleChildNavState(ChildSplashConfiguration.Splash, ChildNavState.Status.RESUMED),
        )
    }

    /**
     * Приложение инициализировано, но навигация все еще в состоянии splash экрана, так как происходит загрузка
     * контента на контент экране.
     */
    data object InitializedSplash : SplashNavState {
        override val children: List<ChildNavState<ChildSplashConfiguration>> = listOf(
            SimpleChildNavState(ChildSplashConfiguration.Content, ChildNavState.Status.RESUMED),
            SimpleChildNavState(ChildSplashConfiguration.Splash, ChildNavState.Status.RESUMED),
        )
    }

    /**
     * Приложение загружено, информация на экране с контентом загружена, отображается экран с контентом.
     */
    data object Content : SplashNavState {
        override val children: List<ChildNavState<ChildSplashConfiguration>> = listOf(
            SimpleChildNavState(ChildSplashConfiguration.Content, ChildNavState.Status.RESUMED),
        )
    }
}

private enum class ChildSplashConfiguration {
    Splash,
    Content,
}
