package ru.vs.control.entities.ui.entities

import kotlinx.coroutines.flow.map
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.core.factoryGenerator.GenerateFactory

@GenerateFactory
internal class EntitiesViewModel(
    entitiesInteractor: EntitiesInteractor,
) : ViewModel() {
    val state = entitiesInteractor.observeEntities()
        .map { it.values.sortedBy { it.id.rawId } }
        .stateIn(emptyList())
}
