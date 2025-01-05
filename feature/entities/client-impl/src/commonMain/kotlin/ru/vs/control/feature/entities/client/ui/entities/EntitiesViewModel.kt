package ru.vs.control.feature.entities.client.ui.entities

import kotlinx.coroutines.flow.map
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.control.feature.entities.client.domain.EntitiesInteractor
import ru.vs.core.factoryGenerator.GenerateFactory

@GenerateFactory
internal class EntitiesViewModel(
    entitiesInteractor: EntitiesInteractor,
) : ViewModel() {
    val state = entitiesInteractor.observeEntities()
        .map { it.values.sortedBy { it.id.rawId } }
        .stateIn(emptyList())
}
