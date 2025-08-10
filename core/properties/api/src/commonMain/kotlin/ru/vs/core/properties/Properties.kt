package ru.vs.core.properties

interface Properties {
    fun <T> getProperty(propertyKey: PropertyKey<T>, defaultValue: T): Property<T>
}
