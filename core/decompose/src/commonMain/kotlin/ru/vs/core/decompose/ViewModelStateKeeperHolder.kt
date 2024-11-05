package ru.vs.core.decompose

import com.arkivanov.essenty.statekeeper.StateKeeper

/**
 * Грязный хак с передачей [StateKeeper] в [ViewModel], необходим потому что мы не хотим явно пробрасывать [StateKeeper]
 * через лямбду [Component.viewModel] и тем самым давать к нему доступ клиентскому коду, так же это упрощает клиентский
 * код, не заставляя явно заниматься пробросом [StateKeeper].
 * При этом хак является безопасным, так как [ViewModel] может создаваться только на главном потоке, следовательно, мы
 * исключаем вероятность гонки потоков.
 */
internal var WhileConstructedViewModelStateKeeper: StateKeeper? = null
