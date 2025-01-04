package ru.vs.control.entities.ui.entities

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory
internal class EntitiesViewModel(
    entitiesInteractor: EntitiesInteractor,
) : ViewModel() {
    val state = entitiesInteractor.observeEntities()
        .map { it.values.sortedBy { it.id.rawId } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
