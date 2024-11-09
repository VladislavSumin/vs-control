package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import androidx.compose.foundation.lazy.LazyListScope

/**
 * Умеет рисовать список добавленных встроенных серверов, а так же обрабатывает клики и другую логику
 * связанную с этим списком.
 */
interface EmbeddedServersListComponent {
    /**
     * Рисует компонент внутри переданного [list].
     */
    fun renderIn(list: LazyListScope)
}
