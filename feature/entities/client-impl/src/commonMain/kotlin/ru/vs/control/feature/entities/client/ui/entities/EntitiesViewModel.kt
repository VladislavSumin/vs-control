package ru.vs.control.feature.entities.client.ui.entities

import kotlinx.coroutines.flow.map
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.entities.client.domain.EntitiesInteractor

@GenerateFactory
internal class EntitiesViewModel(
    entitiesInteractor: EntitiesInteractor,
) : ViewModel() {
    val state = entitiesInteractor.observeEntities()
        .map { it.values.sortedBy { it.id.rawId } }
        .stateIn(emptyList())
}
