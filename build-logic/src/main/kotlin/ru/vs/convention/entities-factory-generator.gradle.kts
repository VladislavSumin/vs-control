package ru.vs.convention

/**
 * Настройка по умолчанию для подключения генерации фабрик сущностей
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vladislavsumin.convention.kmp.ksp-hack")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:entities:factory-generator:api"))
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":feature:entities:factory-generator:ksp"))
}
