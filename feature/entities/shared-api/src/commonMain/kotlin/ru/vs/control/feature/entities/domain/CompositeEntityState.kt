package ru.vs.control.feature.entities.domain

/**
 * Marks "composite" entities.
 *
 * Composite entities is aggregation of some other simple or composite entities to provide
 * complex entity with more than one state
 */
interface CompositeEntityState : EntityState
