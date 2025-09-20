package ru.vs.convention

/**
 * Настройка по умолчанию для подключения генерации прокси классов rSub server модуля
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vladislavsumin.convention.kmp.ksp-hack")
}

dependencies {
    add("kspCommonMainMetadata", project(":rsub:ksp:server"))
}
