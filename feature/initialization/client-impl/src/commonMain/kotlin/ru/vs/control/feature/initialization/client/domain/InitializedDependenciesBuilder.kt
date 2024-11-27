package ru.vs.control.feature.initialization.client.domain

import org.kodein.di.DI

/**
 * Собирает список зависимостей которые должны быть только в initialized app di графе.
 */
fun interface InitializedDependenciesBuilder {
    fun DI.Builder.build()
}
