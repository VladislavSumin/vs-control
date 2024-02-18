package ru.vs.core.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.launch
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.createCoroutineScope

/**
 * TODO компонент пока не готов и находится в процессе разработки.
 *
 * Компонент навигации для реализации splash экрана.
 *
 * @param T тип дочернего компонента.
 * @param awaitInitialization функция для ожидания завершения инициализации приложения.
 * @param splashComponentFactory фабрика для создания компонента splash экрана.
 * @param contentComponentFactory фабрика для создания компонента экрана с контентом. onContentReady необходимо вызвать
 * после того как контентный компонент будет готов к отображению контента, до этого момента будет отображаться splash.
 */
fun <T : ComposeComponent> ComponentContext.childSplash(
    key: String = "child-splash",
    awaitInitialization: suspend () -> Unit,
    splashComponentFactory: (context: ComponentContext) -> T,
    contentComponentFactory: (onContentReady: () -> Unit, context: ComponentContext) -> T,
): Value<ComposeComponent> { // TODO

    val navigationSource = SimpleNavigation<SplashNavEvent>()

    val scope = lifecycle.createCoroutineScope()

    scope.launch {
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
        saveState = { null }, // TODO
        restoreState = { null }, // TODO
        navTransformer = { state, event ->
            when (event) {
                SplashNavEvent.ApplicationInitialized -> SplashNavState.InitializedSplash
                SplashNavEvent.ContentReady -> SplashNavState.Content
            }
        },
        stateMapper = { state, children ->
            children.last().instance!!
        },
        childFactory = { configuration, context ->
            when (configuration) {
                ChildSplashConfiguration.Splash -> splashComponentFactory(context)
                ChildSplashConfiguration.Content -> contentComponentFactory(onContentReady, context)
            }
        }
    )
}

private sealed interface SplashNavEvent {
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
