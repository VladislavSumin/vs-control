package ru.vs.core.navigation

import com.arkivanov.decompose.ComponentContext

/**
 * Контекст экрана.
 * Расширение [ComponentContext] предоставляющее доступ к специфичным для экрана api.
 */
interface ScreenContext : ComponentContext {
    /**
     * Глобальный навигатор через который осуществляется коммуникация между [Screen], [NavigationGraph] и
     * хостами навигации.
     * Вы не должны использовать это api напрямую.
     */
    @NavigationInternalApi
    val globalNavigator: GlobalNavigator
}
