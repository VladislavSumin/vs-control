package ru.vs.convention

/**
 * Настройка по умолчанию для подключения генерации фабрик из модуля navigation:factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.ksp-kmp-hack")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:navigation:factory-generator:api"))
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":core:navigation:factory-generator:ksp"))
}
