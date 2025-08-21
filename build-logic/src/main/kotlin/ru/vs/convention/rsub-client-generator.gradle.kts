package ru.vs.convention

/**
 * Настройка по умолчанию для подключения генерации прокси классов rSub client модуля
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.ksp-kmp-hack")
}

dependencies {
    add("kspCommonMainMetadata", project(":rsub:ksp:client"))
}
