package ru.vs.core.decompose

import com.arkivanov.essenty.instancekeeper.InstanceKeeper

/**
 * Внутренняя прослойка для сохранения [ViewModel], сделана специально что бы [ViewModel] не являлась наследником
 * [InstanceKeeper.Instance] и не могла быть создана напрямую через [InstanceKeeper].
 */
internal class ViewModelHolder<T : ViewModel>(internal val viewModel: T) : InstanceKeeper.Instance {
    override fun onDestroy() = viewModel.onDestroy()
}
