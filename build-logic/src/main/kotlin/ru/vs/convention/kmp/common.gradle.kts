package ru.vs.convention.kmp

/**
 * Базовая настройка KMP.
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.kmp.common-tests")
}

kotlin {
    // Включаем автоматическую генерацию source set`ов. Подробнее читайте документацию по функции.
    applyDefaultHierarchyTemplate()
}
