package ru.vs.core.decompose

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

/**
 * Внутренняя прослойка для сохранения [ViewModel], сделана специально что бы [ViewModel] не являлась наследником
 * [InstanceKeeper.Instance] и не могла быть создана напрямую через [InstanceKeeper].
 */
internal class ViewModelHolder<T : ViewModel>(
    val viewModel: T,
    val viewModelStateKeeper: StateKeeperDispatcher,
) : InstanceKeeper.Instance {
    override fun onDestroy() = viewModel.onDestroy()
}
