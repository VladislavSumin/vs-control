package ru.vs.core.splash

import com.arkivanov.decompose.Child
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vladislavsumin.core.decompose.components.utils.createCoroutineScope

/**
 * Компонент навигации для реализации splash экрана.
 *
 * @param T тип дочернего компонента.
 * @param key ключ навигации, должен быть уникальным в рамках компонента.
 * @param scope [CoroutineScope] для внутренних нужд, жц должен совпадать с lifecycle компонента.
 * @param isInitialized текущее состояние инициализации приложения.
 * @param splashComponentFactory фабрика для создания компонента splash экрана.
 * @param contentComponentFactory фабрика для создания компонента экрана с контентом. onContentReady необходимо вызвать
 * после того как контентный компонент будет готов к отображению контента, до этого момента будет отображаться splash.
 */
fun <T : Any> ComponentContext.childSplash(
    key: String = "child-splash",
    scope: CoroutineScope = lifecycle.createCoroutineScope(),
    isInitialized: StateFlow<Boolean>,
    splashComponentFactory: (context: ComponentContext) -> T,
    contentComponentFactory: (onContentReady: () -> Unit, context: ComponentContext) -> T,
): Value<ChildSplash<T>> {
    val navigationSource = SimpleNavigation<SplashNavEvent>()

    val onContentReady = {
        navigationSource.navigate(SplashNavEvent.ContentReady)
    }

    var isInitializedAtStateRestore = false

    val children = children(
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
            // Тут следует помнить о контракте restoreState, количество и порядок child в конфигурации при сохранении
            // и при восстановлении должны совпадать. Поэтому мы не удаляем Splash из конфигурации в Content состоянии,
            // кажется это меньшая жертва чем другие решения (несколько разных навигаций или другие решения).

            // При восстановлении состояния не смотрим на сохраненное состояние, а восстанавливаемся синхронно
            // в зависимости от статуса инициализации. В случае восстановления состояния при инициализированном
            // приложении мы не можем вернуть splash тут, так как в этом случае decompose удалит инстансы для Content
            // экрана
            if (isInitialized.value) {
                isInitializedAtStateRestore = true
                SplashNavState.InitializedSplash
            } else {
                SplashNavState.Splash
            }
        },
        navTransformer = { _, event ->
            when (event) {
                SplashNavEvent.ApplicationInitialized -> SplashNavState.InitializedSplash
                SplashNavEvent.ContentReady -> SplashNavState.Content
            }
        },
        stateMapper = { state, children ->
            val child = when (state) {
                SplashNavState.Splash,
                SplashNavState.InitializedSplash,
                -> children.last()

                SplashNavState.Content -> children.first()
            }
            // Instance не может быть null, косвенно это проверяется проверкой state.
            ChildSplash(child as Child.Created)
        },
        childFactory = { configuration, context ->
            when (configuration) {
                ChildSplashConfiguration.Splash -> splashComponentFactory(context)
                ChildSplashConfiguration.Content -> contentComponentFactory(onContentReady, context)
            }
        },
    )

    // Запускаем процесс ожидания инициализации только после создания children, так как decompose navigationSource не
    // содержит буфер команд. Таким образом если разместить ожидание инициализации до создания children (который
    // подпишется на навигацию) и само ожидание, за счет Main.immediate диспатчера, окажется мгновенным, то мы потеряем
    // событие навигации.
    if (!isInitializedAtStateRestore) {
        scope.launch(Dispatchers.Main.immediate) {
            isInitialized.first { it } // Дожидаемся инициализации приложения.
            navigationSource.navigate(SplashNavEvent.ApplicationInitialized)
        }
    }

    return children
}

/**
 * Состояние Splash навигации.
 * @param child текущий активный child. Internal так как работа с ним должно осуществляться только через специальный
 * Children объявленный в том же модуле.
 */
data class ChildSplash<T : Any>(
    internal val child: Child.Created<ChildSplashConfiguration, T>,
)

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
            // Оставляем splash в конфигурации из-за особенностей восстановления состояния,
            // подробнее смотрите в restoreState
            SimpleChildNavState(ChildSplashConfiguration.Splash, ChildNavState.Status.DESTROYED),
        )
    }
}

/**
 * Конфигурация дочерних элементов.
 */
// Не может быть internal из-за использования в ChildSplash
enum class ChildSplashConfiguration {
    Splash,
    Content,
}
