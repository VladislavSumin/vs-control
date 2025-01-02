package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.create
import com.arkivanov.essenty.lifecycle.resume
import ru.vs.core.decompose.BaseComponentTest
import ru.vs.core.decompose.ChildTestComponentContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

class ChildListTest : BaseComponentTest() {
    private val state = MutableValue<List<Int>>(emptyList())

    private lateinit var list: Value<List<ChildTestComponentContext<Int>>>

    private fun recreateContextWithSaveInstanceKeeper() {
        recreateContext(RecreateContextType.ConfigurationChange)
        recreateList()
    }

    private fun recreateList() {
        list = context.childList(
            state = state,
            childFactory = ::ChildTestComponentContext,
        )
    }

    @BeforeTest
    fun setupEach() {
        context.lifecycleRegistry.create()
        recreateList()
    }

    @Test
    fun checkInitialConfigurationCreation() {
        state.value = CONFIG_1
        val resultConfig = list.value.map { it.data }
        assertEquals(CONFIG_1, resultConfig)
    }

    @Test
    fun checkRemoveItem() {
        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val notChangingItem = list.value[1]
        val itemMovingToPreviousPosition = list.value[3]
        val removingItem = list.value[2]
        state.value = CONFIG_2

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        assertSame(itemMovingToPreviousPosition, list.value[2])
        assertEquals(Lifecycle.State.RESUMED, itemMovingToPreviousPosition.lifecycle.state)

        assertEquals(Lifecycle.State.DESTROYED, removingItem.lifecycle.state)
    }

    @Test
    fun checkAddItem() {
        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val notChangingItem = list.value[1]
        val itemMovingToNextPosition = list.value[3]
        state.value = CONFIG_3

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        assertSame(itemMovingToNextPosition, list.value[4])
        assertEquals(Lifecycle.State.RESUMED, itemMovingToNextPosition.lifecycle.state)

        val newItem = list.value[3]

        assertEquals(Lifecycle.State.RESUMED, newItem.lifecycle.state)
        assertEquals(5, newItem.data)
    }

    @Test
    fun checkChangeItem() {
        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val notChangingItem = list.value[1]
        val changingItem = list.value[2]
        state.value = CONFIG_4

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        val newItem = list.value[2]
        assertNotEquals(changingItem, newItem)

        assertEquals(Lifecycle.State.RESUMED, newItem.lifecycle.state)
        assertEquals(Lifecycle.State.DESTROYED, changingItem.lifecycle.state)
    }

    @Test
    fun checkMoveItem() {
        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val movingItem = list.value[2]
        state.value = CONFIG_5

        val movedItem = list.value[1]
        assertSame(movingItem, movedItem)
        assertEquals(Lifecycle.State.RESUMED, movedItem.lifecycle.state)
    }

    @Test
    fun checkComponentRecreation() {
        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val item = list.value[1]

        context.lifecycleRegistry.create()
        recreateContextWithSaveInstanceKeeper()

        context.lifecycleRegistry.resume()
        state.value = CONFIG_1
        val newItem = list.value[1]

        assertNotSame(item, newItem)
        assertEquals(Lifecycle.State.DESTROYED, item.lifecycle.state)
        assertEquals(Lifecycle.State.RESUMED, newItem.lifecycle.state)
        assertEquals(item.keepInstanceUniqueValue, newItem.keepInstanceUniqueValue)
    }

    companion object {
        private val CONFIG_1 = listOf(1, 2, 3, 4) // default
        private val CONFIG_2 = listOf(1, 2, 4) // remove one
        private val CONFIG_3 = listOf(1, 2, 3, 5, 4) // add one
        private val CONFIG_4 = listOf(1, 2, 5, 4) // change one
        private val CONFIG_5 = listOf(1, 3, 2, 4) // move one
    }
}
