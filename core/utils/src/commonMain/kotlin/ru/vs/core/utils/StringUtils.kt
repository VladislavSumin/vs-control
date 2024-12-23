package ru.vs.core.utils

// TODO перенести тесты
fun String.decapitalized(): String {
    return this.replaceFirstChar { it.lowercase() }
}
