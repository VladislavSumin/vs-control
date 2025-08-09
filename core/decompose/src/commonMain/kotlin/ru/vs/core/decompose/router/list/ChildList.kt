package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.statekeeper.SerializableContainer
import ru.vs.core.decompose.router.asNavigationSource

/**
 * Создает лист с элементами типа [C] со своим собственным жизненным циклом.
 * При обновлении [state] для одинаковых [C] сохраняет ранее созданные [T].
 *
 * @param C тип конфигурации.
 * @param T тип компонента.
 */
fun <Ctx : GenericComponentContext<Ctx>, C : Any, T : Any> Ctx.childList(
    state: Value<List<C>>,
    key: String = "DefaultChildList",
    childFactory: (configuration: C, Ctx) -> T,
): Value<List<T>> = children(
    source = state.asNavigationSource(),
    key = key,
    initialState = { ListNavState(configurations = state.value) },
    saveState = {
        // Нам не нужно дополнительно сохранение состояния тут, но если вернуть отсюда null,
        // то decompose забудет все состояния дочерних элементов, поэтому возвращаем пустое состояние
        SerializableContainer()
    },
    restoreState = {
        // Когда восстанавливаем состояние то получаем данные не из ранее сохраненного, а из
        // предоставленного нам state состояния.
        ListNavState(state.value)
    },
    navTransformer = { _, event -> ListNavState(configurations = event) },
    stateMapper = { _, children ->
        @Suppress("UNCHECKED_CAST")
        children as List<Child.Created<C, T>>
    },
    childFactory = childFactory,
).map { childList -> childList.map { it.instance } }

private data class ListNavState<out C : Any>(val configurations: List<C>) : NavState<C> {
    override val children: List<SimpleChildNavState<C>> = configurations.map { configuration ->
        SimpleChildNavState(
            configuration = configuration,
            status = ChildNavState.Status.RESUMED,
        )
    }
}
